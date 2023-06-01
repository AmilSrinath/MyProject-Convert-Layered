package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.PaddyStorage;
import lk.ijse.millmaster.dto.tm.PaddyStorageTM;
import lk.ijse.millmaster.model.PaddyStorageModel;
import lk.ijse.millmaster.model.SupplierModel;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ManagePaddyStorageFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public Button btnAddPaddy;
    public Label lblTotal;
    public Button btnCreateBill;
    public Label lblError;
    public Label lblStockID;
    public TableColumn <?, ?> colStatus;

    @FXML
    private AnchorPane ManagePaddyStorageForm;

    @FXML
    private TableView<PaddyStorageTM> tblPaddyStorage;

    public TableColumn <?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableColumn<?, ?> colPaddyType;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQuntity;

    @FXML
    private TableColumn<?, ?> colNoOfBag;

    @FXML
    private TableColumn<?, ?> colSector;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private JFXTextField txtStockID;

    @FXML
    private JFXTextField txtNuberOfBag;

    @FXML
    private JFXTextField txtSector;
    @FXML
    private Button btnCalculatePrice;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtSearchStorage;

    @FXML
    private ComboBox<String> comPaddyType;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private ComboBox<String> comSupplierID;

    @FXML
    private Button btnAddNewSupplier;

    @FXML
    private JFXTextField txtQuntity;
    public StackPane ControllArea;
    ObservableList<PaddyStorageTM> observableList;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Nadu","Red Nadu","Samba","Keerisamba","Red Raw Rice","White Raw Rice");
        comPaddyType.setItems(list);
        loadUserNames();
        getAll();
        setCellValueFactory();
        calculateNetTotal();
        lblError.setVisible(false);
        generateNextUserId();
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblPaddyStorage.getItems().size(); i++) {
            double total  = (double) colQuntity.getCellData(i);
            netTotal += total;
        }
        System.out.println(netTotal);
        lblTotal.setText(String.valueOf(netTotal));
    }

    private void generateNextUserId() {
        try {
            String nextId = PaddyStorageModel.generateNextOrderId();
            lblStockID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewSupplierOnAction(ActionEvent event) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageSupplierForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        Clear();
    }

    void Clear(){
        comPaddyType.setValue("Select Paddy Type");
        comSupplierID.setValue("Select Supplier ID");
        txtStockID.setText("");
        txtNuberOfBag.setText("");
        txtQuntity.setText("");
        txtUnitPrice.setText("");
        txtSector.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM stock WHERE stock_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtStockID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        Clear();
        calculateNetTotal();
        generateNextUserId();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.WARNING,"Pleace Check TextFilds !").show();
            return;
        }

        String Suppilerid = comSupplierID.getValue();
        String Stockid = txtStockID.getText();
        String PaddyType = comPaddyType.getValue();
        Double quntity = Double.valueOf(txtQuntity.getText());
        int NoOfBag = Integer.parseInt(txtNuberOfBag.getText());
        Double UnitPrice = Double.valueOf(txtUnitPrice.getText());
        LocalDate date = LocalDate.now();
        String sector = txtSector.getText();
        double total = UnitPrice * quntity;

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE stock SET Paddy_Type = ?, Paddy_Quntity = ?, No_Of_Bag = ?, Unit_Price = ?, Date = ?, Sector = ?,Total = ? ,Supplier_ID = ? WHERE Stock_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, PaddyType);
            pstm.setDouble(2, quntity);
            pstm.setInt(3, NoOfBag);
            pstm.setDouble(4, UnitPrice);
            pstm.setString(5, String.valueOf(date));
            pstm.setString(6, sector);
            pstm.setDouble(7, total);
            pstm.setString(8, Suppilerid);
            pstm.setString(9, Stockid);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Stock Updated!!").show();
            }
        }
        Clear();
        getAll();
        calculateNetTotal();
        generateNextUserId();
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        lblError.setVisible(false);
        Integer index = tblPaddyStorage.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtStockID.setText(colStockId.getCellData(index).toString());
        comSupplierID.setValue(colSupplierID.getCellData(index).toString());
        txtQuntity.setText(colQuntity.getCellData(index).toString());
        txtNuberOfBag.setText(colNoOfBag.getCellData(index).toString());
        txtSector.setText(colSector.getCellData(index).toString());
        txtUnitPrice.setText(colUnitPrice.getCellData(index).toString());
        comPaddyType.setValue(colPaddyType.getCellData(index).toString());
    }

    public void setControllArea(StackPane controllArea) {
        this.ControllArea=controllArea;
    }

    public void loadUserNames() throws SQLException {
        try{
            List<String> id = SupplierModel.getUserID();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            comSupplierID.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    public void btnAddPaddyOnAction(ActionEvent event) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.WARNING,"Pleace Check TextFilds !").show();
            return;
        }

        String Suppilerid = comSupplierID.getValue();
        String Stockid = lblStockID.getText();
        String PaddyType = comPaddyType.getValue();
        Double quntity = Double.valueOf(txtQuntity.getText());
        int NoOfBag = Integer.parseInt(txtNuberOfBag.getText());
        Double UnitPrice = Double.valueOf(txtUnitPrice.getText());
        LocalDate date = LocalDate.now();
        String sector = txtSector.getText();
        double total = UnitPrice * quntity;

        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO stock(stock_ID , paddy_Type, Paddy_Quntity, No_Of_Bag, Unit_Price, Date, Sector, Total, Supplier_ID,Status) VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,Stockid);
            pstm.setString(2,PaddyType);
            pstm.setDouble(3,quntity);
            pstm.setInt(4,NoOfBag);
            pstm.setDouble(5,UnitPrice);
            pstm.setString(6, String.valueOf(date));
            pstm.setString(7, sector);
            pstm.setDouble(8, total);
            pstm.setString(9,Suppilerid);
            pstm.setString(10,"Available");

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    tblPaddyStorage.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "Paddy Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
            }
        }
        getAll();
        Clear();
        calculateNetTotal();
        generateNextUserId();
    }

    void setCellValueFactory(){
        colStockId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPaddyType.setCellValueFactory(new PropertyValueFactory<>("padddyType"));
        colQuntity.setCellValueFactory(new PropertyValueFactory<>("paddyQuntity"));
        colNoOfBag.setCellValueFactory(new PropertyValueFactory<>("noOfBag"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSector.setCellValueFactory(new PropertyValueFactory<>("sector"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    void getAll() throws SQLException {
        try{
            observableList = FXCollections.observableArrayList();
            List<PaddyStorage> paddyList = PaddyStorageModel.getAll();

            for (PaddyStorage paddyStorage : paddyList){
                observableList.add(new PaddyStorageTM(
                        paddyStorage.getId(),
                        paddyStorage.getPadddyType(),
                        paddyStorage.getPaddyQuntity(),
                        paddyStorage.getNoOfBag(),
                        paddyStorage.getUnitPrice(),
                        paddyStorage.getDate(),
                        paddyStorage.getSector(),
                        paddyStorage.getTotal(),
                        paddyStorage.getSupplierId(),
                        paddyStorage.getStatus()
                ));
            }
            tblPaddyStorage.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }

    public void btnCreateOnAction(ActionEvent event) throws SQLException, JRException {
        String id = txtStockID.getText();
        if (id.equals("")){
            lblError.setVisible(true);
        }else {
            String text = txtStockID.getText();

            JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/Laporan1.jrxml");
            JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);

            Map<String,Object> data = new HashMap<>();
            data.put("Parameter1",text);

            JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, data, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasPrint,false);
        }
    }

    public void txtNoOfBagOnKeyReleaed(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT,txtNuberOfBag);
    }

    public void txtSectorOnKeyReleaed(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.ADDRESS,txtSector);
    }

    public void txtUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.DOUBLE,txtUnitPrice);
    }

    public void txtQuntityOnKeyReleaed(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT,txtQuntity);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.INT,txtNuberOfBag))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtSector))return false;
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtUnitPrice))return false;
        if (!Regex.setTextColor(TextFilds.INT,txtQuntity))return false;
        return true;
    }
}
