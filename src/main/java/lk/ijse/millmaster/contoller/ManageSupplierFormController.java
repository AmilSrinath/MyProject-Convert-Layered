package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.millmaster.dto.Buyer;
import lk.ijse.millmaster.dto.Supplier;
import lk.ijse.millmaster.dto.User;
import lk.ijse.millmaster.dto.tm.BuyerTM;
import lk.ijse.millmaster.dto.tm.SupplierTM;
import lk.ijse.millmaster.model.BuyerModel;
import lk.ijse.millmaster.model.OrderModel;
import lk.ijse.millmaster.model.SupplierModel;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;

public class ManageSupplierFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public TableView<SupplierTM> tblSupplier;
    public Label lblTime;
    public Label lblSupplierID;

    @FXML
    private AnchorPane ManageSupplierForm;


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
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtSearchBuyer;

    @FXML
    private JFXTextField txtSupplierContact;

    @FXML
    private JFXTextField txtSupplierNIC;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;
    ObservableList<SupplierTM> observableList;

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

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String name = txtSupplierName.getText();
        String contact = txtSupplierContact.getText();
        String nic = txtSupplierNIC.getText();
        String address = txtSupplierAddress.getText();

        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO supplier(supplier_ID , supplier_Name, supplier_Contact,supplier_nic,supplier_Address) VALUES(?,?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,lblSupplierID.getText());
            pstm.setString(2,name);
            pstm.setString(3,contact);
            pstm.setString(4,nic);
            pstm.setString(5,address);

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Assest Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
            }
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

    void getAll() throws SQLException {
        observableList = FXCollections.observableArrayList();
        List<Supplier> supplierList = SupplierModel.getAll();

        for ( Supplier supplier: supplierList){
            observableList.add(new SupplierTM(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContact(),
                    supplier.getNic(),
                    supplier.getAddress()
            ));
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

    private void generateNextSupplierID() {
        try {
            String nextId = SupplierModel.generateNextOrderId();
            lblSupplierID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM Supplier WHERE Supplier_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtSupplierID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        generateNextSupplierID();
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        txtSupplierNIC.setText("");
        txtSupplierAddress.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String id = txtSupplierID.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierContact.getText();
        String nic = txtSupplierNIC.getText();
        String address = txtSupplierAddress.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Supplier SET Supplier_Name = ?, Supplier_Contact = ?, Supplier_NIC=?, Supplier_Address=? WHERE Supplier_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, contact);
            pstm.setString(3, nic);
            pstm.setString(4, address);
            pstm.setString(5, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Buyer Updated!!").show();
            }
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
