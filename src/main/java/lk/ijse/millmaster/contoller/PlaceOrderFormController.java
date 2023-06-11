package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.PlaceOrderBO;
import lk.ijse.millmaster.dao.Custom.PlaceOrderDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.CartDTO;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.dto.tm.PlaceOrderTM;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class PlaceOrderFormController implements Initializable {
    public AnchorPane PlaceOrderForm;
    public Label lblProductCount;
    public Label lblProductName;
    public TableColumn colAction;
    public Label lblOrderID;
    public Label lblNetTotal;
    public Button btnPlaceOrder;
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtQuntity;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private TableView<PlaceOrderTM> tblPlaceOrder;

    @FXML
    private TableColumn<?, ?> colProductID;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colQuntity;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private ComboBox<String> comProductID;
    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    StackPane controllArea;

    PlaceOrderBO productBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);
    PlaceOrderDAO placeOrderDAO = (PlaceOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PLACEORDER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProductID();
        setCellValueFactory();
//        getAll();
    }

    private void loadProductID() throws SQLException {
        List<String> id = placeOrderDAO.loadProductID();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id){
            obList.add(un);
        }
        comProductID.setItems(obList);
    }

    @FXML
    void btnCalculatePaymentOnAction(ActionEvent event) {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        if (txtQuntity.getStyle().equalsIgnoreCase("-fx-background-color: null")) {
            String pid = comProductID.getValue();
            String oid = lblOrderID.getText();
            int quntity = Integer.parseInt(txtQuntity.getText());
            Double unitPrice = Double.valueOf(txtUnitPrice.getText());
            Double total = quntity * unitPrice;

            Button btnRemove = new Button("Remove");
            Image image = new Image("/image/icons8-remove-48.png");
            ImageView view = new ImageView(image);
            view.setFitHeight(20);
            view.setFitWidth(20);
            btnRemove.setGraphic(view);
            btnRemove.setCursor(Cursor.HAND);
            setRemoveBtnOnAction(btnRemove);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                    if (colProductID.getCellData(i).equals(pid) & colOrderID.getCellData(i).equals(oid)) {
                        quntity += (int) colQuntity.getCellData(i);
                        total = quntity * unitPrice;
                        obList.get(i).setQuntity(quntity);
                        obList.get(i).setTotal(total);
                        tblPlaceOrder.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }
            PlaceOrderTM tm = new PlaceOrderTM(pid, oid, quntity, unitPrice, total, btnRemove);
            obList.add(tm);
            tblPlaceOrder.setItems(obList);
            calculateNetTotal();
        }else {
            new Alert(Alert.AlertType.ERROR, "Quntity Invalid !!").show();
        }
    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblPlaceOrder.refresh();
                calculateNetTotal();
            }
        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    void setCellValueFactory(){
        colProductID.setCellValueFactory(new PropertyValueFactory<>("pid"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("oid"));
        colQuntity.setCellValueFactory(new PropertyValueFactory<>("quntity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnCloseOnMouseClicked(MouseEvent event) throws IOException {
        close();
    }

    void close() throws IOException {
        root.getScene().getWindow().hide();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageOrderForm.fxml"));
        AnchorPane anchorPane = loader.load();
        controllArea.getChildren().removeAll();
        controllArea.getChildren().setAll(anchorPane);
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {

    }

    public void comProductIDOnAction(ActionEvent event) throws SQLException {
        String code = comProductID.getSelectionModel().getSelectedItem();
        txtQuntity.requestFocus();
        ProductDTO product = placeOrderDAO.searchById(code);
        fillItemFields(product);
    }

    private void fillItemFields(ProductDTO product) {
        lblProductName.setText(product.getType());
        lblProductCount.setText(String.valueOf(product.getQuntity()));
    }

    public void txtQuntityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.INT,txtQuntity);
        try {
            int typeNumber = Integer.parseInt(txtQuntity.getText());
            int stock = Integer.parseInt(lblProductCount.getText());
            if (typeNumber > stock | typeNumber == 0) {
                txtQuntity.setStyle("-fx-background-color: red");
            } else {
                txtQuntity.setStyle("-fx-background-color: null");
            }
        }catch (Exception ex){
        }
    }

    public void setID(String id, StackPane controllArea) {
        lblOrderID.setText(id);
        this.controllArea=controllArea;
    }

    public void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, IOException, JRException {
        String oid = lblOrderID.getText();
        String id = lblOrderID.getText();
        System.out.println(id);

        List<CartDTO> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            PlaceOrderTM tm = obList.get(i);

            CartDTO cartDTO = new CartDTO(tm.getPid(), tm.getQuntity(), tm.getUnitPrice());
            cartDTOList.add(cartDTO);
        }

        boolean isPlaced = productBO.placeOrder(oid, cartDTOList);
        if(isPlaced) {
            CreateBill(id);
        } else {
            new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
        }
        close();
    }

    void CreateBill(String id) throws JRException, SQLException {
        JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/productReport.jrxml");
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("OrderID",id);
        data.put("NetTotal",NetTotalCalculate(id));

        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, data, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasPrint,false);
    }

    String NetTotalCalculate(String id) throws SQLException {
        return String.valueOf(placeOrderDAO.NetTotalCalculate(id));
    }

    public void txtUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.DOUBLE,txtUnitPrice);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.INT,txtQuntity))return false;
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtUnitPrice))return false;
        return true;
    }
}
