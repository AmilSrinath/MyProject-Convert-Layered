package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.AssestBO;
import lk.ijse.millmaster.dao.Custom.AssestDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.tm.AssestTM;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class ManageAssestFormController implements Initializable {
    public AnchorPane ManageAssestForm;
    public JFXTextField txtQuntity;
    public Label lblUserName;
    public Label lblUserID;
    public TableColumn<?, ?> colUserID;
    public Label lblAssestID;
    @FXML
    private TableColumn<?, ?> colAssestId;

    @FXML
    private TableColumn<?, ?> colAssestName;

    @FXML
    private TableColumn<?, ?> colAssestQuntity;

    @FXML
    private TableView<AssestTM> tblAssest;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchAssest;
    ObservableList<AssestTM> observableList;

    AssestBO assestBO = (AssestBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ASSEST);
    AssestDAO assestDAO = (AssestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ASSEST);

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
//        searchFilter();
        getAll();
        generateNextAssestID();
    }

    private void generateNextAssestID() throws SQLException, ClassNotFoundException {
        String nextId = assestBO.generateNewAssestID();
        lblAssestID.setText(nextId);
    }

    public void rowOnMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        Integer index = tblAssest.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtID.setText(colAssestId.getCellData(index).toString());
        txtName.setText(colAssestName.getCellData(index).toString());
        txtQuntity.setText(colAssestQuntity.getCellData(index).toString());
    }

    private void searchFilter(){
        FilteredList<AssestTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearchAssest.setOnKeyReleased(e->{
            txtSearchAssest.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super AssestTM>) assest->{
                    if (newValue.isEmpty() || newValue.isBlank()){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (assest.getId().toLowerCase().contains(searchKeyword)){
                        return true;
                    }else if(assest.getName().toLowerCase().contains(searchKeyword)){
                        return true;
                    }else if(assest.getUserID().toLowerCase().contains(searchKeyword)){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<AssestTM> assests = new SortedList<>(filterData);
            assests.comparatorProperty().bind(tblAssest.comparatorProperty());
            tblAssest.setItems(assests);
        });
    }


    public void btnSaveOnAction(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isValidated()) {
            new Alert(Alert.AlertType.ERROR, "Pleace Check TextFilds !").show();
            return;
        }
        String Uid = assestDAO.searchByName(lblUserName.getText());
        lblUserID.setText(Uid);

        String id = lblAssestID.getText();
        String name = txtName.getText();
        int quntity = Integer.parseInt(txtQuntity.getText());

        if (assestBO.addAssest(new AssestDTO(id,name,quntity,lblUserID.getText()))){
            new Alert(Alert.AlertType.CONFIRMATION, "Assest Saved !").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "SQL Error !").show();
        }

        txtID.setText("");
        txtName.setText("");
        txtQuntity.setText("");
        generateNextAssestID();
        getAll();
    }

    void getAll() throws SQLException {
        try {
            observableList = FXCollections.observableArrayList();
            List<AssestDTO> allAssests = assestBO.getAllAssest();

            for (AssestDTO a : allAssests) {
                observableList.add(new AssestTM(a.getId(), a.getName(),a.getQun(),a.getUserID()));
            }
            tblAssest.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory(){
        colAssestId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAssestName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAssestQuntity.setCellValueFactory(new PropertyValueFactory<>("qun"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    @SneakyThrows
    public void btnDeleteOnAction(javafx.event.ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if(!assestBO.deleteAssest(txtID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
            }
        }

        getAll();
        txtID.setText("");
        txtName.setText("");
        txtQuntity.setText("");
        generateNextAssestID();
    }

    @SneakyThrows
    public void btnUpdateOnAction(javafx.event.ActionEvent actionEvent) {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }
        String Uid = assestDAO.searchByName(lblUserName.getText());
        lblUserID.setText(Uid);

        String id = txtID.getText();
        String name = txtName.getText();
        int qun = Integer.parseInt(txtQuntity.getText());
        String UID = lblUserID.getText();

        if(assestBO.updateAssest(new AssestDTO(id,name,qun,UID))){
            new Alert(Alert.AlertType.CONFIRMATION, "Assest Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        getAll();
        generateNextAssestID();
    }

    public void btnClearOnAction(javafx.event.ActionEvent actionEvent) {
        txtID.setText("");
        txtName.setText("");
        txtQuntity.setText("");
    }

    public void setLabe(String UserName) {
        lblUserName.setText(String.valueOf(UserName));
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtName);
    }

    public void txtQuntityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INVOICE,txtQuntity);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtName))return false;
        if (!Regex.setTextColor(TextFilds.INVOICE,txtQuntity))return false;
        return true;
    }
}
