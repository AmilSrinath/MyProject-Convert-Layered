package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.millmaster.dao.Custom.ForgotPasswordDAO;
import lk.ijse.millmaster.dao.DAOFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgotPassword3Contoller implements Initializable {
    public ImageView btnClose;
    public AnchorPane root;

    @FXML
    private JFXTextField txtPassword1;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtReEnterPassword1;

    @FXML
    private JFXPasswordField txtReEnterPassword;
    ForgotPasswordDAO forgotPasswordDAO = (ForgotPasswordDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FORGOTPASSWORD);

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setPasswordDisable();
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
            if(!forgotPasswordDAO.updatePassword(userName,password)){
                new Alert(Alert.AlertType.ERROR,"SQL Error !!");
            }
            root.getScene().getWindow().hide();
        }else {
            txtReEnterPassword.setStyle("-fx-background-color: #e74c3c;");
            txtReEnterPassword1.setStyle("-fx-background-color: #e74c3c;");
        }
    }

    public void btnCloseOnAction(MouseEvent mouseEvent) {
        root.getScene().getWindow().hide();
    }
}
