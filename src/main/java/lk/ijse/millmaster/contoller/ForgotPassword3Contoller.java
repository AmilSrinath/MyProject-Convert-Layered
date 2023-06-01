package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ForgotPassword3Contoller implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public ImageView btnClose;
    public AnchorPane root;

    @FXML
    private JFXTextField txtPassword1;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private ImageView hidePassword;

    @FXML
    private ImageView hidePassword1;

    @FXML
    private JFXTextField txtReEnterPassword1;

    @FXML
    private JFXPasswordField txtReEnterPassword;

    @FXML
    private Button btnSave;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setPasswordDisable();
    }

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {

    }

    @FXML
    void showPasswordOnMousePresseds(MouseEvent event) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMousePresseds1(MouseEvent event) {
        txtReEnterPassword.setVisible(false);
        txtReEnterPassword1.setText(txtReEnterPassword.getText());
        txtReEnterPassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMouseReleased(MouseEvent event) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);
    }

    @FXML
    void showPasswordOnMouseReleased1(MouseEvent event) {
        txtReEnterPassword.setVisible(true);
        txtReEnterPassword1.setVisible(false);
    }

    @FXML
    void txtReEnterPasswordOnInputMethodTextChanged(KeyEvent event) {

    }

    @FXML
    void txtReEnterPasswordOnKeyPresed(KeyEvent event) {

    }

    void setPasswordDisable(){
        txtPassword1.setVisible(false);
        txtReEnterPassword1.setVisible(false);
    }

    public void btnSaveOnAction(ActionEvent event) throws SQLException, IOException {
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String userName = ForgotPasswordFormController.userName;

        if (password.equalsIgnoreCase(reEnterPassword)) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "UPDATE User SET User_password = ? WHERE User_Name = ?";

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, password);
                pstm.setString(2, userName);

                if (pstm.executeUpdate() > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Password Updated!!").show();
                }
                txtReEnterPassword.setStyle("-fx-background-color: none;");
                txtReEnterPassword1.setStyle("-fx-background-color: none;");
            }
            root.getScene().getWindow().hide();
//            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.show();
        }else {
            txtReEnterPassword.setStyle("-fx-background-color: #e74c3c;");
            txtReEnterPassword1.setStyle("-fx-background-color: #e74c3c;");
        }
    }

    public void btnCloseOnAction(MouseEvent mouseEvent) {
        root.getScene().getWindow().hide();
    }
}
