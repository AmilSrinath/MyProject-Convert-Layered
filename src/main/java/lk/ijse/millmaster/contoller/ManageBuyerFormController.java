package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.BuyerBO;
import lk.ijse.millmaster.bo.Custom.ProductBO;
import lk.ijse.millmaster.dao.Custom.BuyerDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.BuyerDTO;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.dto.tm.BuyerTM;
import lk.ijse.millmaster.dto.tm.UserTM;
import lk.ijse.millmaster.model.BuyerModel;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageBuyerFormController implements Initializable {
    public javafx.scene.control.Label lblUserName;
    public Label lblUserID;
    public TableColumn<?, ?> colUserID;
    public Label lblBuyerID;
    @FXML
    private TableView<BuyerTM> tblBuyer;

    @FXML
    private TableColumn<?, ?> colBuyerId;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private TableColumn<?, ?> colBuyerContact;

    @FXML
    private TableColumn<?, ?> colBuyerShopName;

    @FXML
    private TableColumn<?, ?> colBuyerAddress;

    @FXML
    private JFXTextField txtBuyerID;

    @FXML
    private JFXTextField txtBuyerName;

    @FXML
    private JFXTextField txtSearchBuyer;

    @FXML
    private JFXTextField txtBuyerContact;

    @FXML
    private JFXTextField txtBuyerShopName;

    @FXML
    private JFXTextField txtBuyerAddress;

    ObservableList<BuyerTM> observableList;

    BuyerBO buyerBO = (BuyerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BUYER);
    BuyerDAO buyerDAO = (BuyerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BUYER);

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        searchFilter();
        generateNextBuyerID();
    }

    private void generateNextBuyerID() throws SQLException, ClassNotFoundException {
        String nextId = buyerBO.generateNewBuyerID();
        lblBuyerID.setText(nextId);
    }

    @SneakyThrows
    private void searchFilter(){
        FilteredList<BuyerTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearchBuyer.setOnKeyReleased(e->{
            txtSearchBuyer.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super BuyerTM>) buyer->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (buyer.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(buyer.getId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(buyer.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(buyer.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(buyer.getShopName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<BuyerTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblBuyer.comparatorProperty());
            tblBuyer.setItems(buyer);
        });
    }

    public void btnSaveOnAction(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String user = buyerDAO.searchByName(lblUserName.getText());
        lblUserID.setText(user);

        String id = lblBuyerID.getText();
        String name = txtBuyerName.getText();
        String contact = txtBuyerContact.getText();
        String shop = txtBuyerShopName.getText();
        String address = txtBuyerAddress.getText();
        String uid = lblUserID.getText();

        if(buyerBO.addBuyer(new BuyerDTO(id,name,contact,shop,address,uid))){
            new Alert(Alert.AlertType.CONFIRMATION,"Buyer Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }

        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerContact.setText("");
        txtBuyerShopName.setText("");
        txtBuyerAddress.setText("");
        generateNextBuyerID();
        getAll();
    }

    void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<BuyerDTO> allCustomers = buyerBO.getAllBuyer();

        for (BuyerDTO b : allCustomers) {
            observableList.add(new BuyerTM(b.getId(), b.getName(), b.getContact(),b.getShopName(),b.getAddress(),b.getUserID()));
        }
        tblBuyer.setItems(observableList);
    }

    void setCellValueFactory(){
        colBuyerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBuyerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colBuyerShopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colBuyerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    @SneakyThrows
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!buyerBO.deleteBuyer(txtBuyerID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }
        }
        getAll();
        generateNextBuyerID();
        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerContact.setText("");
        txtBuyerAddress.setText("");
        txtBuyerShopName.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtBuyerID.getText();
        String name = txtBuyerName.getText();
        String contact = txtBuyerContact.getText();
        String shop = txtBuyerShopName.getText();
        String address = txtBuyerAddress.getText();
        String uid = lblUserID.getText();

        String user = buyerDAO.searchByName(lblUserName.getText());
        lblUserID.setText(user);

        if (buyerBO.updateBuyer(new BuyerDTO(id,name,contact,shop,address,uid))){
            new Alert(Alert.AlertType.CONFIRMATION, "Buyer Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerContact.setText("");
        txtBuyerAddress.setText("");
        txtBuyerShopName.setText("");
        getAll();
        generateNextBuyerID();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerContact.setText("");
        txtBuyerAddress.setText("");
        txtBuyerShopName.setText("");
    }

    public void rowOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblBuyer.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtBuyerID.setText(colBuyerId.getCellData(index).toString());
        txtBuyerAddress.setText(colBuyerAddress.getCellData(index).toString());
        txtBuyerContact.setText(colBuyerContact.getCellData(index).toString());
        txtBuyerName.setText(colBuyerName.getCellData(index).toString());
        txtBuyerShopName.setText(colBuyerShopName.getCellData(index).toString());
    }

    public void setLabe(String text) {
        lblUserName.setText(String.valueOf(text));
    }


    public void txtBuyerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtBuyerName);
    }

    public void txtBuyerContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.PHONE,txtBuyerContact);
    }

    public void txtBuyerShopOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtBuyerShopName);
    }

    public void txtBuyerAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.ADDRESS,txtBuyerAddress);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtBuyerName))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtBuyerAddress))return false;
        if (!Regex.setTextColor(TextFilds.NAME,txtBuyerShopName))return false;
        if (!Regex.setTextColor(TextFilds.PHONE,txtBuyerContact))return false;
        return true;
    }

    public void OnMouseClicktxtSearchBuyer(MouseEvent mouseEvent) {
        searchFilter();
    }
}
