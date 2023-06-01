package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lk.ijse.millmaster.dto.Assest;
import lk.ijse.millmaster.dto.User;
import lk.ijse.millmaster.dto.tm.AssestTM;
import lk.ijse.millmaster.dto.tm.UserTM;
import lk.ijse.millmaster.model.AssestModel;
import lk.ijse.millmaster.model.BuyerModel;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class ManageAssestFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

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
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtSearchAssest;
    ObservableList<AssestTM> observableList;

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
//        searchFilter();
        getAll();
        generateNextAssestID();
    }

    private void generateNextAssestID() {
        try {
            String nextId = AssestModel.generateNextAssestId();
            lblAssestID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (assest.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(assest.getId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<AssestTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblAssest.comparatorProperty());
            tblAssest.setItems(buyer);
        });
    }


    public void btnSaveOnAction(javafx.event.ActionEvent actionEvent) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        try {
            User user = UserModel.searchByName(lblUserName.getText());
            fillItemFields(user);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        String id = lblAssestID.getText();
        String name = txtName.getText();
        int quntity = Integer.parseInt(txtQuntity.getText());


        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO assest(assest_ID , assest_Name, assest_quntity,User_ID) VALUES(?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3, String.valueOf(quntity));
            pstm.setString(4,lblUserID.getText());

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Assest Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
            }
        }
        txtID.setText("");
        txtName.setText("");
        txtQuntity.setText("");
        generateNextAssestID();
        getAll();
    }

    private void fillItemFields(User user) {
        lblUserID.setText(user.getId());
    }

    void getAll() throws SQLException {
        try{
            observableList = FXCollections.observableArrayList();
            List<Assest> assestList = AssestModel.getAll();

            for (Assest assest : assestList){
                observableList.add(new AssestTM(
                        assest.getId(),
                        assest.getName(),
                        assest.getQun(),
                        assest.getUserID()
                ));
            }

            tblAssest.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
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
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM assest WHERE Assest_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

        try {
            User user = UserModel.searchByName(lblUserName.getText());
            fillItemFields(user);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

        String id = txtID.getText();
        String name = txtName.getText();
        int qun = Integer.parseInt(txtQuntity.getText());
        String UID = lblUserID.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Assest SET Assest_name = ?, Assest_quntity = ?,User_ID = ? WHERE Assest_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, qun);
            pstm.setString(3,UID);
            pstm.setString(4, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Assest Updated!!").show();
            }
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
