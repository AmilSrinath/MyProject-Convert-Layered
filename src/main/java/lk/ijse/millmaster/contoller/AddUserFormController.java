package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public AnchorPane ManageUserForm;
    public ImageView hidePassword;
    public ImageView hidePassword1;
    public JFXTextField txtEmail;
    public JFXTextField txtNIC;
    public JFXTextField txtName;
    public JFXTextField txtUserID;
    public Button btnSave;
    public Label btnClose;
    public JFXPasswordField txtReEnterPassword;
    public JFXPasswordField txtPassword;
    public JFXTextField txtPassword1;
    public JFXTextField txtReEnterPassword1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword1.setVisible(false);
        txtReEnterPassword1.setVisible(false);
    }

    public void showPasswordOnMousePresseds(MouseEvent mouseEvent) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);
    }

    public void showPasswordOnMouseReleased(MouseEvent mouseEvent) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);
    }

    public void showPasswordOnMousePresseds1(MouseEvent mouseEvent) {
        txtReEnterPassword.setVisible(false);
        txtReEnterPassword1.setText(txtReEnterPassword.getText());
        txtReEnterPassword1.setVisible(true);
    }

    public void showPasswordOnMouseReleased1(MouseEvent mouseEvent) {
        txtReEnterPassword.setVisible(true);
        txtReEnterPassword1.setVisible(false);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtUserID.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();

        if (password.equalsIgnoreCase(reEnterPassword)){
            try(Connection con = DriverManager.getConnection(URL,props)){
                String sql = "INSERT INTO user(User_ID , User_Name, User_Password,User_NIC, User_Email) VALUES(?,?,?,?,?)";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1,id);
                pstm.setString(2,name);
                pstm.setString(3,password);
                pstm.setString(4,nic);
                pstm.setString(5,email);

                try {
                    int affectedRows = pstm.executeUpdate();
                    if (affectedRows > 0) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer Added !!").show();
                    }
                }catch (Exception ex){
                    new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
                }
            }
            txtReEnterPassword.setStyle("-fx-background-color: 333333;");
            txtUserID.setText("");
            txtNIC.setText("");
            txtName.setText("");
            txtPassword.setText("");
            txtReEnterPassword.setText("");
            txtEmail.setText("");
        }else {
            txtReEnterPassword.setStyle("-fx-background-color: #e74c3c;");
            txtReEnterPassword1.setStyle("-fx-background-color: #e74c3c;");
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        ManageUserFormController.root.getScene().getWindow().hide();
    }

    public void btnCloseOnMouseClicked(MouseEvent mouseEvent) {
        ManageUserFormController.root.getScene().getWindow().hide();
    }

    public void txtReEnterPasswordOnInputMethodTextChanged(KeyEvent keyEvent) {
        txtReEnterPassword.setStyle("-fx-background-color: 333333;");
        txtReEnterPassword1.setStyle("-fx-background-color: 333333;");
    }
}
