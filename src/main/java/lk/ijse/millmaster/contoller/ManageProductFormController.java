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
import lk.ijse.millmaster.bo.Custom.ProductBO;
import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.PaddyStorage;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.dto.tm.ProductTM;
import lk.ijse.millmaster.model.PaddyStorageModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageProductFormController implements Initializable {

    public DatePicker txtManufactureDate;
    public DatePicker txtExpireDate;
    public Label lblProductID;
    public ComboBox <String> comStockID;
    public TableColumn <?, ?> colStockID;
    public Label lblProductID1;
    public Label lblPaddyType;
    public Label lblProductID11;
    public Label lblQuntity;
    public JFXTextField txtPaddyQuntity;
    public Label lblError;
    public TableColumn<?, ?> colPaddyQun;

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
    private JFXTextField txtSearchProduct;

    @FXML
    private ComboBox<String> comProductType;
    ObservableList<ProductTM> observableList;

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

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

    private void loadStockID() throws SQLException {
        List<String> id = productDAO.loadStockID();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id){
            obList.add(un);
        }
        comStockID.setItems(obList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        Clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(!productBO.deleteProduct(txtProductID.getText())){
            new Alert(Alert.AlertType.ERROR,"SQL Error !").show();
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
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String Pid = lblProductID.getText();
        String Sid = comStockID.getValue();
        String paddyType = comProductType.getValue();
        int quntity = Integer.parseInt(txtQuntity.getText());
        int paddyquntity = Integer.parseInt(txtPaddyQuntity.getText());
        String manufactureDate = String.valueOf(txtManufactureDate.getValue());
        String expireDate = String.valueOf(txtExpireDate.getValue());
        String style = txtPaddyQuntity.getStyle();

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to save?", yes, no).showAndWait();

        if (!style.equalsIgnoreCase("-fx-background-color: red")) {
            if (result.orElse(no) == yes) {
                if (!productBO.addProduct(new ProductDTO(Pid,quntity,paddyquntity,paddyType,manufactureDate,expireDate,Sid))){
                    new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
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

    private void getAll() {
        try {
            observableList = FXCollections.observableArrayList();
            List<ProductDTO> allProducts = productBO.getAllProduct();

            for (ProductDTO p : allProducts) {
                observableList.add(new ProductTM(p.getId(), p.getQuntity(),p.getPaddyQun(),p.getType(),p.getManufact(),p.getExpire(),p.getSid()));
            }
            tblProduct.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory(){
        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductQuntity.setCellValueFactory(new PropertyValueFactory<>("quntity"));
        colPaddyQun.setCellValueFactory(new PropertyValueFactory<>("paddyQun"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colManufactureDate.setCellValueFactory(new PropertyValueFactory<>("manufact"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("expire"));
        colStockID.setCellValueFactory(new PropertyValueFactory<>("sid"));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFilds !").show();
            return;
        }

        String id = txtProductID.getText();
        String paddyType = comProductType.getValue();
        int quntity = Integer.parseInt(txtQuntity.getText());
        int paddyQun = Integer.parseInt(txtPaddyQuntity.getText());
        String manufactureDate = String.valueOf(txtManufactureDate.getValue());
        String expireDate = String.valueOf(txtExpireDate.getValue());
        String Sid = comStockID.getValue();

        if (!productBO.updateProduct(new ProductDTO(id,quntity,paddyQun,paddyType,manufactureDate,expireDate,Sid))) {
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Update Complete !").show();
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
        txtPaddyQuntity.setText(colPaddyQun.getCellData(index).toString());
        comProductType.setValue(colProductType.getCellData(index).toString());
        txtManufactureDate.setValue(LocalDate.parse(colManufactureDate.getCellData(index).toString()));
        txtExpireDate.setValue(LocalDate.parse(colExpireDate.getCellData(index).toString()));
    }

    private void generateNextProductID() throws SQLException, ClassNotFoundException {
        String nextId = productBO.generateNewProductID();
        lblProductID.setText(nextId);
    }

    void Clear(){
        txtProductID.setText("");
        txtPaddyQuntity.setText("");
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
        if (!Regex.setTextColor(TextFilds.INT,txtQuntity)) return false;
        if (!Regex.setTextColor(TextFilds.INT,txtPaddyQuntity)) return false;
        return true;
    }
}
