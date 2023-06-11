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
import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.entity.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {
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

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

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

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtUserID.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String nic = txtNIC.getText();
        String email = txtEmail.getText();

        if (password.equalsIgnoreCase(reEnterPassword)){
            if(userDAO.add(new User(id,name,password,nic,email))){
                new Alert(Alert.AlertType.CONFIRMATION, "User Added !!").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
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
