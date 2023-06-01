package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.User;
import lk.ijse.millmaster.dto.tm.SupplierTM;
import lk.ijse.millmaster.dto.tm.UserTM;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ManageUserFormController implements Initializable{

    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public AnchorPane ManageUserForm;
    public static Parent root;
    public TableView<UserTM> tblUser;
    public JFXTextField txtUserID;
    public JFXTextField txtName;
    public JFXTextField txtNIC;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword1;
    public JFXPasswordField txtPassword;
    public ImageView hidePassword;
    public ImageView hidePassword1;
    public JFXTextField txtReEnterPassword1;
    public JFXPasswordField txtReEnterPassword;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnClear;
    public JFXTextField txtSearchUser;
    public VBox SearchBarVBox;
    public Label lblUserID;
    public Label lblError;

    ObservableList<UserTM> observableList;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserPassword;

    @FXML
    private TableColumn<?, ?> colUserNic;

    @FXML
    private TableColumn<?, ?> colUserEmail;
    public Button btnUser;


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        setPasswordDisable();
        searchFilter();
        generateNextUserId();
//        txtUserID.setText(String.valueOf(lblUserID.getText()));
    }

    private void searchFilter(){
        FilteredList<UserTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearchUser.setOnKeyReleased(e->{
            txtSearchUser.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super UserTM>) user->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (user.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(user.getId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(user.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(user.getEmail().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(user.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<UserTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblUser.comparatorProperty());
            tblUser.setItems(buyer);
        });
    }

    private void generateNextUserId() {
        try {
            String nextId = UserModel.generateNextOrderId();
            lblUserID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory(){
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colUserNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    void getAll() throws SQLException {
        try{
            observableList = FXCollections.observableArrayList();
            List<User> userList = UserModel.getAll();

            for (User user : userList){
                observableList.add(new UserTM(
                   user.getId(),
                   user.getName(),
                   user.getPassword(),
                   user.getNic(),
                   user.getEmail()
                ));
            }
            tblUser.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }

    public void txtReEnterPasswordOnInputMethodTextChanged(KeyEvent keyEvent) {

    }

    public void btnCancelOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.WARNING,"Pleace Check TextFilds !").show();
            return;
        }

        String id = txtUserID.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();

        if (password.equalsIgnoreCase(reEnterPassword)) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "INSERT INTO user(User_ID , User_Name, User_Password,User_NIC, User_Email) VALUES(?,?,?,?,?)";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, lblUserID.getText());
                pstm.setString(2, name);
                pstm.setString(3, password);
                pstm.setString(4, nic);
                pstm.setString(5, email);


                try {
                    int affectedRows = pstm.executeUpdate();
                    if (affectedRows > 0) {
                        tblUser.refresh();
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer Added !!").show();
                    }
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "This ID has been previously used!!").show();
                }
            }
            txtReEnterPassword.setStyle("-fx-background-color: 333333;");
            txtUserID.setText("");
            txtNIC.setText("");
            txtName.setText("");
            txtPassword.setText("");
            txtReEnterPassword.setText("");
            txtEmail.setText("");

            } else {
                txtReEnterPassword.setStyle("-fx-background-color: #e74c3c;");
                txtReEnterPassword1.setStyle("-fx-background-color: #e74c3c;");
                lblError.setText("Plase Check Password !");
            }
        getAll();
        generateNextUserId();
    }

    public void showPasswordOnMousePresseds1(MouseEvent mouseEvent) {
        txtReEnterPassword.setVisible(false);
        txtReEnterPassword1.setText(txtReEnterPassword.getText());
        txtReEnterPassword1.setVisible(true);
    }

    public void showPasswordOnMousePresseds(MouseEvent mouseEvent) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);
    }

    public void showPasswordOnMouseReleased1(MouseEvent mouseEvent) {
        txtReEnterPassword.setVisible(true);
        txtReEnterPassword1.setVisible(false);
    }

    public void showPasswordOnMouseReleased(MouseEvent mouseEvent) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);
    }

    public void rowOnMouseClicked(MouseEvent mouseEvent) {
        txtEmail.setStyle("-fx-background-color: null");
        Integer index = tblUser.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        System.out.println(">>>>> "+index);
        txtUserID.setText(colUserId.getCellData(index).toString());
        txtName.setText(colUserName.getCellData(index).toString());
        txtPassword.setText(colUserPassword.getCellData(index).toString());
        txtReEnterPassword.setText(colUserPassword.getCellData(index).toString());
        txtNIC.setText(colUserNic.getCellData(index).toString());
        txtEmail.setText(colUserEmail.getCellData(index).toString());
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM user WHERE User_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtUserID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        txtUserID.setText("");
        txtNIC.setText("");
        txtName.setText("");
        txtPassword.setText("");
        txtReEnterPassword.setText("");
        txtEmail.setText("");
        generateNextUserId();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.WARNING,"Pleace Check TextFilds !").show();
            return;
        }

        String id = txtUserID.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();

        if (password.equalsIgnoreCase(reEnterPassword)) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "UPDATE User SET User_name = ?, User_password = ?, User_nic = ?, User_email = ?  WHERE User_id = ?";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, name);
                pstm.setString(2, password);
                pstm.setString(3, nic);
                pstm.setString(4, email);
                pstm.setString(5, id);

                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!!").show();
                }
                txtReEnterPassword.setStyle("-fx-background-color: none;");
                txtReEnterPassword1.setStyle("-fx-background-color: none;");
            }
        } else {
            txtReEnterPassword.setStyle("-fx-background-color: #e74c3c;");
            txtReEnterPassword1.setStyle("-fx-background-color: #e74c3c;");
            lblError.setText("Plase Check Password !");
        }
        getAll();
        generateNextUserId();
    }

    public void txtReEnterPasswordOnKeyPresed(KeyEvent keyEvent) {
        txtReEnterPassword.setStyle("-fx-background-color: none;");
        txtReEnterPassword1.setStyle("-fx-background-color: none;");
        lblError.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtUserID.setText("");
        txtNIC.setText("");
        txtName.setText("");
        txtPassword.setText("");
        txtReEnterPassword.setText("");
        txtEmail.setText("");
    }

    void setPasswordDisable(){
        txtPassword1.setVisible(false);
        txtReEnterPassword1.setVisible(false);
    }

    public void EmailOnKeyReleased(KeyEvent keyEvent) {
        lblError.setText("");
        txtEmail.setStyle("-fx-background-color: null;");
    }

    public void txtNameKeyOnReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtName);
    }

    public void txtNICKeyOnReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.LANKA_ID,txtNIC);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.EMAIL,txtEmail);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtName))return false;
        if (!Regex.setTextColor(TextFilds.LANKA_ID,txtNIC))return false;
        if (!Regex.setTextColor(TextFilds.EMAIL,txtEmail))return false;
        return true;
    }
}

