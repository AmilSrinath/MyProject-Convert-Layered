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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.millmaster.dto.Order;
import lk.ijse.millmaster.dto.tm.OrderTM;
import lk.ijse.millmaster.model.BuyerModel;
import lk.ijse.millmaster.model.OrderModel;
import lk.ijse.millmaster.model.PaddyStorageModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class ManageOrderFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public DatePicker txtDate;
    public TableView<OrderTM> tblOrder;
    public ComboBox comPaddyType;
    public Button btnPlaceOrder;
    public Label lblSelectRow;
    public TableColumn <?, ?> colStatus;
    public Label lblOrderID;
    @FXML
    private AnchorPane ManageOrderForm;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colOrderQuntity;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtQuntity;

    @FXML
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtBuyerID;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private ComboBox<String> comBuyerName;
    ObservableList<OrderTM> observableList;
    StackPane controllArea;
    public Label lblActiveOrders;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserNames();
        getAll();
        setCellValueFactory();
        lblSelectRow.setVisible(false);
        generateNextOderID();
    }

    private void generateNextOderID() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            lblOrderID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM orders WHERE Order_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        generateNextOderID();
        OrderActive();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        Object buyerid = comBuyerName.getSelectionModel().getSelectedItem();
        String buyerID = (String) buyerid;
        System.out.println(buyerID);

        String id = txtID.getText();
        String date = String.valueOf(txtDate.getValue());

        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO orders(order_ID , order_Date, Buyer_ID, Status) VALUES(?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,lblOrderID.getText());
            pstm.setString(2,date);
            pstm.setString(3,buyerID);
            pstm.setString(4,"Active");

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    tblOrder.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.ERROR, "This ID has been previously used!!").show();
                System.out.println(ex);
            }
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

    private void getAll() throws SQLException {
        observableList = FXCollections.observableArrayList();
        List<Order> orders = OrderModel.getAll();

        for (Order order : orders){
            observableList.add(new OrderTM(
                    order.getId(),
                    order.getDate(),
                    order.getBuyerId(),
                    order.getStatus()
            ));
        }
        tblOrder.setItems(observableList);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        LocalDate value = txtDate.getValue();
        String Bid = comBuyerName.getValue();
        String id = txtID.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE orders SET Order_Date = ?, Buyer_ID = ? WHERE Order_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, String.valueOf(value));
            pstm.setString(2, Bid);
            pstm.setString(3,id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Stock Updated!!").show();
            }
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

    public void btnPlaceOrderOnAction(ActionEvent event) throws IOException, SQLException {
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
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT Count(Status)FROM orders WHERE Status='Active'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblActiveOrders.setText(String.valueOf(sum));
        }
    }

    public void setActiveOrders(Label lblActiveOrders) {
        this.lblActiveOrders=lblActiveOrders;
    }

}
