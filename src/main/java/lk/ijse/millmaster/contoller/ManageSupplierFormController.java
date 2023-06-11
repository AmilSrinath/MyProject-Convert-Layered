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
import lk.ijse.millmaster.bo.Custom.SupplierBO;
import lk.ijse.millmaster.dto.SupplierDTO;
import lk.ijse.millmaster.dto.tm.SupplierTM;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class ManageSupplierFormController implements Initializable {
    public TableView<SupplierTM> tblSupplier;
    public Label lblSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colSupplierContact;

    @FXML
    private TableColumn<?, ?> colSupplierNIC;

    @FXML
    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private JFXTextField txtSupplierID;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSearchBuyer;

    @FXML
    private JFXTextField txtSupplierContact;

    @FXML
    private JFXTextField txtSupplierNIC;

    @FXML
    private JFXTextField txtSupplierAddress;
    ObservableList<SupplierTM> observableList;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        searchFilter();
        generateNextSupplierID();
    }

    public void rowOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtSupplierID.setText(colSupplierId.getCellData(index).toString());
        txtSupplierName.setText(colSupplierName.getCellData(index).toString());
        txtSupplierContact.setText(colSupplierContact.getCellData(index).toString());
        txtSupplierNIC.setText(colSupplierNIC.getCellData(index).toString());
        txtSupplierAddress.setText(colSupplierAddress.getCellData(index).toString());
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String id = lblSupplierID.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierContact.getText();
        String nic = txtSupplierNIC.getText();
        String address = txtSupplierAddress.getText();

        if (supplierBO.addSupplier(new SupplierDTO(id,name,contact,nic,address))){
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        txtSupplierNIC.setText("");
        txtSupplierAddress.setText("");
        generateNextSupplierID();
        getAll();
    }

    private void searchFilter(){
        FilteredList<SupplierTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearchBuyer.setOnKeyReleased(e->{
            txtSearchBuyer.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super SupplierTM>) supplier->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (supplier.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(supplier.getId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(supplier.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(supplier.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(supplier.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<SupplierTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblSupplier.comparatorProperty());
            tblSupplier.setItems(buyer);
        });
    }

    void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<SupplierDTO> allCustomers = supplierBO.getAllSupplier();

        for (SupplierDTO s : allCustomers) {
            observableList.add(new SupplierTM(s.getId(), s.getName(), s.getContact(),s.getNic(),s.getAddress()));
        }
        tblSupplier.setItems(observableList);
    }

    void setCellValueFactory(){
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSupplierNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void generateNextSupplierID() throws ClassNotFoundException, SQLException {
        String nextId = supplierBO.generateNewSupplierID();
        lblSupplierID.setText(nextId);
    }

    @SneakyThrows
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (!supplierBO.deleteSupplier(txtSupplierID.getText())){
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
        }

        getAll();
        generateNextSupplierID();
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        txtSupplierNIC.setText("");
        txtSupplierAddress.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String id = txtSupplierID.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierContact.getText();
        String nic = txtSupplierNIC.getText();
        String address = txtSupplierAddress.getText();

        if (supplierBO.updateSupplier(new SupplierDTO(id,name,contact,nic,address))){
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
        }

        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        txtSupplierNIC.setText("");
        txtSupplierAddress.setText("");
        generateNextSupplierID();
        getAll();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        txtSupplierNIC.setText("");
        txtSupplierAddress.setText("");
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtSupplierName);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.PHONE,txtSupplierContact);
    }

    public void txtNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.LANKA_ID,txtSupplierNIC);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.ADDRESS,txtSupplierAddress);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtSupplierName))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtSupplierAddress))return false;
        if (!Regex.setTextColor(TextFilds.PHONE,txtSupplierContact))return false;
        if (!Regex.setTextColor(TextFilds.LANKA_ID,txtSupplierNIC))return false;
        return true;
    }
}
