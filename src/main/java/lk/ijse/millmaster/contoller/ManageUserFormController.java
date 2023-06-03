package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.UserBO;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.dto.tm.UserTM;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

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
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        setPasswordDisable();
        searchFilter();
        generateNextUserId();
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

    private void generateNextUserId() throws ClassNotFoundException {
        try {
            String nextId = userBO.generateNewUserID();
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
        try {
            observableList = FXCollections.observableArrayList();
            List<UserDTO> allCustomers = userBO.getAllUsers();

            for (UserDTO u : allCustomers) {
                observableList.add(new UserTM(u.getId(), u.getName(), u.getPassword(),u.getNic(),u.getEmail()));
            }
            tblUser.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtReEnterPasswordOnInputMethodTextChanged(KeyEvent keyEvent) {}

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.WARNING,"Pleace Check TextFilds !").show();
            return;
        }

        String id = lblUserID.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();

        if (password.equalsIgnoreCase(reEnterPassword)) {
            if (userBO.addUser(new UserDTO(id,name,password,nic,email))){
                tblUser.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added !!").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "This ID has been previously used!!").show();
            }

            txtReEnterPassword.setStyle("-fx-background-color: 333333;");
            Clear();

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
        txtUserID.setText(colUserId.getCellData(index).toString());
        txtName.setText(colUserName.getCellData(index).toString());
        txtPassword.setText(colUserPassword.getCellData(index).toString());
        txtReEnterPassword.setText(colUserPassword.getCellData(index).toString());
        txtNIC.setText(colUserNic.getCellData(index).toString());
        txtEmail.setText(colUserEmail.getCellData(index).toString());
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!userBO.deleteUser(txtUserID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
            }
        }
        getAll();
        Clear();
        generateNextUserId();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
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
            if (userBO.updateUser(new UserDTO(id,name,password,nic,email))){
                tblUser.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated !!").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
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
        Clear();
    }

    void Clear(){
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

    public void OnMouseClicktxtSearchUser(MouseEvent mouseEvent) {
        searchFilter();
    }
}

