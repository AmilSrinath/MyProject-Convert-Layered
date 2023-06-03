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
import lk.ijse.millmaster.dto.Buyer;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.dto.tm.BuyerTM;
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
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public javafx.scene.control.Label lblUserName;
    public Label lblUserID;
    public TableColumn<?, ?> colUserID;
    public Label lblBuyerID;
    @FXML
    private AnchorPane ManageBuyerForm;

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
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtSearchBuyer;

    @FXML
    private JFXTextField txtBuyerContact;

    @FXML
    private JFXTextField txtBuyerShopName;

    @FXML
    private JFXTextField txtBuyerAddress;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;
    ObservableList<BuyerTM> observableList;

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        searchFilter();
        generateNextBuyerID();
    }

    private void generateNextBuyerID() {
        try {
            String nextId = BuyerModel.generateNextBuyerId();
            lblBuyerID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void btnSaveOnAction(javafx.event.ActionEvent actionEvent) throws SQLException {
        try {
            UserDTO user = UserModel.searchByName(lblUserName.getText());
            fillItemFields(user);
//            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        String id = txtBuyerID.getText();
        String name = txtBuyerName.getText();
        String contact = txtBuyerContact.getText();
        String shop = txtBuyerShopName.getText();
        String address = txtBuyerAddress.getText();


        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO buyer(Buyer_ID , Buyer_Name, Buyer_Cont_Num,Buyer_Shop,Buyer_Address,User_ID) VALUES(?,?,?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,lblBuyerID.getText());
            pstm.setString(2,name);
            pstm.setString(3,contact);
            pstm.setString(4,shop);
            pstm.setString(5,address);
            pstm.setString(6,lblUserID.getText());

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Assest Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
            }
        }
        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerContact.setText("");
        txtBuyerShopName.setText("");
        txtBuyerAddress.setText("");
        generateNextBuyerID();
        getAll();
    }

    void getAll() throws SQLException {
        try{
            observableList = FXCollections.observableArrayList();
            List<Buyer> buyerList = BuyerModel.getAll();

            for ( Buyer buyer: buyerList){
                observableList.add(new BuyerTM(
                        buyer.getId(),
                        buyer.getName(),
                        buyer.getContact(),
                        buyer.getShopName(),
                        buyer.getAddress(),
                        buyer.getUserID()
                ));
            }

            tblBuyer.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }

    void setCellValueFactory(){
        colBuyerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBuyerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colBuyerShopName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colBuyerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    private void fillItemFields(UserDTO user) {
        lblUserID.setText(user.getId());
    }

    @SneakyThrows
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM buyer WHERE Buyer_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtBuyerID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtBuyerID.getText();
        String name = txtBuyerName.getText();
        String contact = txtBuyerContact.getText();
        String shop = txtBuyerShopName.getText();
        String address = txtBuyerAddress.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Buyer SET Buyer_Name = ?, Buyer_Cont_Num = ?, Buyer_Shop=?, Buyer_Address=? WHERE Buyer_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, contact);
            pstm.setString(3, shop);
            pstm.setString(4, address);
            pstm.setString(5, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Buyer Updated!!").show();
            }
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
}
