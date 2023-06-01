package lk.ijse.millmaster.contoller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPassword2Controller implements Initializable {

    public AnchorPane AnchorPaneFogotPassword2;
    public Label lblotp;
    public Label lblStatus;
    public Label lblTimeCount;
    public AnchorPane AnchorPaneFogotPassword22;
    public Button btn1;
    public ImageView btnClose;
    @FXML
    private TextField txtOTPNum1;

    @FXML
    private TextField txtOTPNum2;

    @FXML
    private TextField txtOTPNum3;

    @FXML
    private TextField txtOTPNum4;
    public int OTP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblotp.setText(String.valueOf(ForgotPasswordFormController.OTP));

    }

    @FXML
    void txtOTPNum1OnKeyTyped(KeyEvent event) {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]")){
            txtOTPNum1.setStyle("-fx-border-color: red");
        }else {
            txtOTPNum1.setStyle("-fx-border-color: null");
        }
    }

    @FXML
    void txtOTPNum2OnKeyTyped(KeyEvent event) {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]")){
            txtOTPNum2.setStyle("-fx-border-color: red");
        }else {
            txtOTPNum2.setStyle("-fx-border-color: null");
        }
    }

    @FXML
    void txtOTPNum3OnKeyTyped(KeyEvent event) {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]")){
            txtOTPNum3.setStyle("-fx-border-color: red");
        }else {
            txtOTPNum3.setStyle("-fx-border-color: null");
        }
    }

    @FXML
    void txtOTPNum4OnKeyTyped(KeyEvent event) {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]")){
            txtOTPNum4.setStyle("-fx-border-color: red");
        }else {
            txtOTPNum4.setStyle("-fx-border-color: null");
        }
    }

    public void txtOTPNum1OnKeyReleased(KeyEvent keyEvent) {
        txtOTPNum2.requestFocus();
    }

    public void txtOTPNum2OnKeyReleased(KeyEvent keyEvent) {
        txtOTPNum3.requestFocus();
    }

    public void txtOTPNum3OnKeyReleased(KeyEvent keyEvent) throws IOException {
        txtOTPNum4.requestFocus();
    }

    public void txtOTPNum4OnKeyReleased(KeyEvent keyEvent) throws IOException {
        String otp1 = txtOTPNum1.getText();
        String otp2 = txtOTPNum2.getText();
        String otp3 = txtOTPNum3.getText();
        String otp4 = txtOTPNum4.getText();

        String setOtp=otp1+otp2+otp3+otp4;
        if (lblotp.getText().equalsIgnoreCase(setOtp)){
            lblStatus.setStyle("-fx-text-fill: Green");
            lblStatus.setText("Currect OTP");

            Parent root = FXMLLoader.load(getClass().getResource("/view/ForgotPassword3.fxml"));
            Scene scene = btn1.getScene();
            root.translateXProperty().set(scene.getWidth());

            AnchorPane parentContainer = (AnchorPane) scene.getRoot();
            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().removeAll(AnchorPaneFogotPassword2);
            });
            timeline.play();

        }else {
            lblStatus.setStyle("-fx-text-fill: Red");
            lblStatus.setText("Invalid OTP");
        }
    }

    public void btnCloseOnAction(MouseEvent mouseEvent) {
        AnchorPaneFogotPassword2.getScene().getWindow().hide();
    }
}
