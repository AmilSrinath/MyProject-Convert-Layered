package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.millmaster.dto.User;
import lk.ijse.millmaster.model.UserModel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable{
    public ImageView btnClose;
    public Label btnForgotPass;
    public VBox UsernameVbox;
    public AnchorPane root1;
    public JFXPasswordField txtPassword;
    public VBox VBOXPassword;
    public VBox PasswordVbox;
    public ImageView showPassword;
    public JFXTextField txtPassword1;
    public ComboBox comUsername;
    public Button btnSignIn;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword1.setVisible(false);
        loadUserNames();
        comUsername.getSelectionModel().select("Admin");
    }

    public void btnCloseOnMouseClicked(MouseEvent mouseEvent) {
        System.exit(1);
    }

    public void btnForgotPassOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ForgotPasswordForm.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        btnSignInOnAction();
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

    public void loadUserNames() throws SQLException {
        try{
            List<String> username = UserModel.getUserName();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : username){
                obList.add(un);
            }
            comUsername.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
    }

    public void txtPasswordOnKeyTyped(KeyEvent keyEvent) {
        PasswordVbox.setStyle("-fx-background-color: none;");
        txtPassword.setStyle("-fx-text-fill: #FFFFFF;");
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        btnSignInOnAction();
    }

    private void btnSignInOnAction() {
        String user_name = (String) comUsername.getSelectionModel().getSelectedItem();
        try{
            User user = UserModel.searchByUser_Name(user_name);

            if (user.getPassword().equalsIgnoreCase(txtPassword.getText())){
                FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/HomeForm.fxml"));
                AnchorPane anchorPane = loader.load();
                HomeFormController controller = loader.getController();
                controller.setLabe(user_name);
                Scene scene = new Scene(anchorPane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
                root1.getScene().getWindow().hide();
            }else {
                PasswordVbox.setStyle("-fx-background-color: #e74c3c;");
            }
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pleace Select User!").show();
        }
    }
}
