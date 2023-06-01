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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.millmaster.dto.PaddyStorage;
import lk.ijse.millmaster.dto.Product;
import lk.ijse.millmaster.dto.tm.BuyerTM;
import lk.ijse.millmaster.dto.tm.EmployeeTM;
import lk.ijse.millmaster.dto.tm.ProductTM;
import lk.ijse.millmaster.model.PaddyStorageModel;
import lk.ijse.millmaster.model.PlaceOrderModel;
import lk.ijse.millmaster.model.ProductModel;
import lk.ijse.millmaster.model.SupplierModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageProductFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public DatePicker txtManufactureDate;
    public DatePicker txtExpireDate;
    public Label lblProductID;
    public ComboBox <String> comStockID;
    public TableColumn <?, ?> colStockID;
    public ComboBox <String> comPaddyID;
    public Label lblProductID1;
    public Label lblPaddyType;
    public Label lblProductID11;
    public Label lblQuntity;
    public JFXTextField txtPaddyQuntity;
    public Label lblError;
    @FXML
    private AnchorPane ManageUserForm;

    @FXML
    private TableView<ProductTM> tblProduct;

    @FXML
    private TableColumn<?, ?> colProductID;

    @FXML
    private TableColumn<?, ?> colProductType;

    @FXML
    private TableColumn<?, ?> colProductQuntity;

    @FXML
    private TableColumn<?, ?> colManufactureDate;

    @FXML
    private TableColumn<?, ?> colExpireDate;

    @FXML
    private JFXTextField txtProductID;

    @FXML
    private JFXTextField txtQuntity;

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
    private JFXTextField txtSearchProduct;

    @FXML
    private ComboBox<String> comProductType;
    ObservableList<ProductTM> observableList;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Nadu 5Kg","Nadu 10Kg","Nadu 25Kg","Red Nadu 5Kg","Red Nadu 10Kg","Red Nadu 25Kg","Samba 5Kg","Samba 10Kg","Samba 25Kg","Keerisamba 5Kg","Keerisamba 10Kg","Keerisamba 25Kg","Red Raw Rice 5Kg","Red Raw Rice 10Kg","Red Raw Rice 25Kg","White Raw Rice 5Kg","White Raw Rice 10Kg","White Raw Rice 25Kg");
        comProductType.setItems(list);
        setCellValueFactory();
        getAll();
        searchFilter();
        generateNextProductID();
        loadStockID();
    }

    private void loadStockID() {
        try{
            List<String> id = PaddyStorageModel.getStockID();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            comStockID.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        Clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM production WHERE Product_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtProductID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        Clear();
        generateNextProductID();
    }

    private void searchFilter(){
        FilteredList<ProductTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearchProduct.setOnKeyReleased(e->{
            txtSearchProduct.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super ProductTM>) product->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (product.getId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(product.getType().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(product.getManufact().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(product.getExpire().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<ProductTM> product = new SortedList<>(filterData);
            product.comparatorProperty().bind(tblProduct.comparatorProperty());
            tblProduct.setItems(product);
        });
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String Sid = comStockID.getValue();
        String paddyType = comProductType.getValue();
        String quntity = txtQuntity.getText();
        String paddyquntity = txtPaddyQuntity.getText();
        String manufactureDate = String.valueOf(txtManufactureDate.getValue());
        String expireDate = String.valueOf(txtExpireDate.getValue());
        String style = txtPaddyQuntity.getStyle();

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to save?", yes, no).showAndWait();

        if (!style.equalsIgnoreCase("-fx-background-color: red")) {
            if (result.orElse(no) == yes) {
                try (Connection con = DriverManager.getConnection(URL, props)) {
                    String sql = "INSERT INTO production(Product_ID , Product_Quntity, Product_Type, Product_Manufact, Product_Expire, Stock_ID) VALUES(?,?,?,?,?,?)";

                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setString(1, lblProductID.getText());
                    pstm.setString(2, quntity);
                    pstm.setString(3, paddyType);
                    pstm.setString(4, manufactureDate);
                    pstm.setString(5, expireDate);
                    pstm.setString(6, Sid);

                    try {
                        int affectedRows = pstm.executeUpdate();
                        if (affectedRows > 0) {

                        }
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
                    }

                    boolean isAdded = ProductModel.addProduct(Sid, paddyquntity);
                    if (isAdded) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Order is Placed!").show();
                        setComStockID();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
                    }
                }
            }
        }else {
            lblError.setText("Paddy Quntity Invalid !");
        }
        txtProductID.setText("");
        txtQuntity.setText("");

        getAll();
        generateNextProductID();
    }

    private void getAll() throws SQLException {
        observableList = FXCollections.observableArrayList();
        List<Product> productList = ProductModel.getAll();

        for ( Product product: productList){
            observableList.add(new ProductTM(
                    product.getId(),
                    product.getQuntity(),
                    product.getType(),
                    product.getManufact(),
                    product.getExpire(),
                    product.getSid()
            ));
        }
        tblProduct.setItems(observableList);
    }

    void setCellValueFactory(){
        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductQuntity.setCellValueFactory(new PropertyValueFactory<>("quntity"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colManufactureDate.setCellValueFactory(new PropertyValueFactory<>("manufact"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("expire"));
        colStockID.setCellValueFactory(new PropertyValueFactory<>("sid"));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String id = txtProductID.getText();
        String paddyType = comProductType.getValue();
        String quntity = txtQuntity.getText();
        String manufactureDate = String.valueOf(txtManufactureDate.getValue());
        String expireDate = String.valueOf(txtExpireDate.getValue());

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Production SET Product_Quntity = ?, Product_Type=?, Product_Manufact=?, Product_Expire=? WHERE Product_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, quntity);
            pstm.setString(2, paddyType);
            pstm.setString(3, manufactureDate);
            pstm.setString(4, expireDate);
            pstm.setString(5, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated!!").show();
            }
        }
        getAll();
        Clear();
        generateNextProductID();
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        Integer index = tblProduct.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtProductID.setText(colProductID.getCellData(index).toString());
        txtQuntity.setText(colProductQuntity.getCellData(index).toString());
        comProductType.setValue(colProductType.getCellData(index).toString());
        txtManufactureDate.setValue(LocalDate.parse(colManufactureDate.getCellData(index).toString()));
        txtExpireDate.setValue(LocalDate.parse(colExpireDate.getCellData(index).toString()));
    }

    private void generateNextProductID() {
        try {
            String nextId = ProductModel.generateNextOrderId();
            lblProductID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void Clear(){
        txtProductID.setText("");
        txtQuntity.setText("");
        txtExpireDate.setValue(null);
        txtManufactureDate.setValue(null);
        comProductType.setValue(null);
    }

    public void comStockIDOnAction(ActionEvent event) {
        txtPaddyQuntity.requestFocus();
        setComStockID();
    }

    void setComStockID(){
        String code = comStockID.getSelectionModel().getSelectedItem();
        try {
            PaddyStorage paddyStorage = PaddyStorageModel.searchById(code);
            fillItemFields(paddyStorage);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillItemFields(PaddyStorage paddyStorage) {
        lblPaddyType.setText(paddyStorage.getPadddyType());
        lblQuntity.setText(String.valueOf(paddyStorage.getPaddyQuntity()));
    }

    public void txtPaddyQuntityOnKeyPressed(KeyEvent keyEvent) {

    }

    public void txtPaddyQuntityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT,txtPaddyQuntity);

        try {
            Double type = Double.valueOf(txtPaddyQuntity.getText());

            if (type <= 0) {
                txtPaddyQuntity.setStyle("-fx-background-color: red");
            } else {
                txtPaddyQuntity.setStyle("-fx-background-color: null");
            }
        }catch (NumberFormatException ex){}
    }

    public void txtQuntityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT,txtQuntity);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.INT,txtQuntity))return false;
        if (!Regex.setTextColor(TextFilds.INT,txtPaddyQuntity))return false;
        return true;
    }
}
