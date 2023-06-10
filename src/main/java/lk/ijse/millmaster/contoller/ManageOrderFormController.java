package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.OrderBO;
import lk.ijse.millmaster.dao.Custom.OrderDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.OrderDTO;
import lk.ijse.millmaster.dto.tm.OrderTM;
import lk.ijse.millmaster.model.BuyerModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageOrderFormController implements Initializable {
    public DatePicker txtDate;
    public TableView<OrderTM> tblOrder;
    public Button btnPlaceOrder;
    public Label lblSelectRow;
    public TableColumn <?, ?> colStatus;
    public Label lblOrderID;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtBuyerID;

    @FXML
    private ComboBox<String> comBuyerName;
    ObservableList<OrderTM> observableList;
    StackPane controllArea;
    public Label lblActiveOrders;

    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserNames();
        getAll();
        setCellValueFactory();
        lblSelectRow.setVisible(false);
        generateNextOderID();
    }

    private void generateNextOderID() throws ClassNotFoundException, SQLException {
        String nextId = orderBO.generateNewOrderID();
        lblOrderID.setText(nextId);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if(!orderBO.deleteOrder(txtID.getText())){
                new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
            }
        }
        getAll();
        generateNextOderID();
        OrderActive();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Object buyerid = comBuyerName.getSelectionModel().getSelectedItem();
        String buyerID = (String) buyerid;
        System.out.println(buyerID);

        String id = lblOrderID.getText();
        String date = String.valueOf(txtDate.getValue());

        if(orderBO.addOrder(new OrderDTO(id,date,buyerID,"Active"))){
            new Alert(Alert.AlertType.CONFIRMATION,"Order Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
        }

        txtID.setText("");
        txtBuyerID.setText("");
        getAll();
        generateNextOderID();
        OrderActive();
    }

    void setCellValueFactory(){
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("buyerId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<OrderDTO> allCustomers = orderBO.getAllOrder();

        for (OrderDTO o : allCustomers) {
            observableList.add(new OrderTM(o.getId(),o.getDate(),o.getBuyerId(),o.getStatus()));
        }
        tblOrder.setItems(observableList);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String value = String.valueOf(txtDate.getValue());
        String Bid = comBuyerName.getValue();
        String id = txtID.getText();

        if (orderBO.updateOrder(new OrderDTO(id,value,Bid,"Active"))){
            new Alert(Alert.AlertType.CONFIRMATION, "Order Updated !!").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        getAll();
        generateNextOderID();
        OrderActive();
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        lblSelectRow.setVisible(false);
        Integer index = tblOrder.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        comBuyerName.setValue(colBuyerName.getCellData(index).toString());
        txtDate.setValue(LocalDate.parse(colOrderDate.getCellData(index).toString()));
        txtID.setText(colOrderID.getCellData(index).toString());
    }

    public void loadUserNames() throws SQLException {
        try{
            List<String> id = BuyerModel.getUserID();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : id){
                obList.add(un);
            }
            comBuyerName.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String id = txtID.getText();
        int index = tblOrder.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            String s = colStatus.getCellData(index).toString();
            if(!s.equals("Complete")) {
                if (!id.equals("")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlaceOrderForm.fxml"));
                    AnchorPane anchorPane = loader.load();
                    PlaceOrderFormController controller = loader.getController();
                    controller.setID(id,controllArea);
                    Scene scene = new Scene(anchorPane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                } else {
                    lblSelectRow.setVisible(true);
                }
            }else {
                lblSelectRow.setText("This Order is completed !!");
                lblSelectRow.setVisible(true);
            }
        }else {
            lblSelectRow.setVisible(true);
        }
        generateNextOderID();
        OrderActive();
    }

    public void setControllArea(StackPane controllArea, Label lblActiveOrders) {
        this.controllArea=controllArea;
        this.lblActiveOrders=lblActiveOrders;
    }

    void OrderActive() throws SQLException {
        lblActiveOrders.setText(String.valueOf(orderDAO.OrderActive()));
    }

    public void setActiveOrders(Label lblActiveOrders) {
        this.lblActiveOrders=lblActiveOrders;
    }
}
