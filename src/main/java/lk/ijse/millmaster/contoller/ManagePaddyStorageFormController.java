package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.PaddyStorageBO;
import lk.ijse.millmaster.dao.Custom.PaddyStorageDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.PaddyStorageDTO;
import lk.ijse.millmaster.dto.tm.PaddyStorageTM;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ManagePaddyStorageFormController implements Initializable {
    public Button btnAddPaddy;
    public Label lblTotal;
    public Button btnCreateBill;
    public Label lblError;
    public Label lblStockID;
    public TableColumn <?, ?> colStatus;

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
    private ComboBox<String> comPaddyType;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private ComboBox<String> comSupplierID;

    @FXML
    private JFXTextField txtQuntity;
    public StackPane ControllArea;
    ObservableList<PaddyStorageTM> observableList;

    PaddyStorageBO paddyStorageBO = (PaddyStorageBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PADDYSTORAGE);
    PaddyStorageDAO paddyStorageDAO = (PaddyStorageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PADDYSTORAGE);

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
        generateNextStockId();
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

    private void generateNextStockId() throws SQLException, ClassNotFoundException {
        String nextId = paddyStorageBO.generateNewPaddyStorageID();
        lblStockID.setText(nextId);
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
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if(!paddyStorageBO.deletePaddyStorage(txtStockID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
            }
        }
        getAll();
        Clear();
        calculateNetTotal();
        generateNextStockId();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
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
        String date = String.valueOf(LocalDate.now());
        String sector = txtSector.getText();
        double total = UnitPrice * quntity;

        if(paddyStorageBO.updatePaddyStorage(new PaddyStorageDTO(Stockid,PaddyType,quntity,NoOfBag,UnitPrice,date,sector,total,Suppilerid,"Available"))){
            new Alert(Alert.AlertType.CONFIRMATION, "Paddy Storage Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        Clear();
        getAll();
        calculateNetTotal();
        generateNextStockId();
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
        List<String> id = paddyStorageDAO.loadUserNames();
        ObservableList<String> obList = FXCollections.observableArrayList();
        for (String un : id){
            obList.add(un);
        }
        comSupplierID.setItems(obList);
    }

    public void btnAddPaddyOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
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
        String date = String.valueOf(LocalDate.now());
        String sector = txtSector.getText();
        double total = UnitPrice * quntity;

        if(paddyStorageBO.addPaddyStorage(new PaddyStorageDTO(Stockid,PaddyType,quntity,NoOfBag,UnitPrice,date,sector,total,Suppilerid,"Available"))){
            new Alert(Alert.AlertType.CONFIRMATION, "Paddy Storage Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        getAll();
        Clear();
        calculateNetTotal();
        generateNextStockId();
    }

    void setCellValueFactory(){
        colStockId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPaddyType.setCellValueFactory(new PropertyValueFactory<>("paddyType"));
        colQuntity.setCellValueFactory(new PropertyValueFactory<>("paddyQuntity"));
        colNoOfBag.setCellValueFactory(new PropertyValueFactory<>("noOfBag"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSector.setCellValueFactory(new PropertyValueFactory<>("sector"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<PaddyStorageDTO> allPaddyStorages = paddyStorageBO.getAllPaddyStorages();

        for (PaddyStorageDTO p : allPaddyStorages) {
            observableList.add(new PaddyStorageTM(p.getId(),p.getPaddyType(),p.getPaddyQuntity(),p.getNoOfBag(),p.getUnitPrice(),p.getDate(),p.getSector(),p.getTotal(),p.getSupplierId(),p.getStatus()));
        }
        tblPaddyStorage.setItems(observableList);
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
