package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {

    public JFXToggleButton btnTheme;
    public Button btnManagePaddyStorage;
    public Button btnManageEmployee;
    public Button btnManageOrder;
    public Button btnManageSupplier;
    public Button btnManageBuyer;
    public Button btnManageAssest;
    public Button btnDashboard;
    public Button btnManageUser;
    public Button btnManageProduct;

    public VBox SearchBarVBox;
    public JFXTextField SearchBar;
    public ImageView BackgroundImg;
    public Label lblDate;
    public Label lblTime;
    public Label lblUserName;
    public ImageView btnSettingWhite;
    public ImageView btnMassageWhite;
    public ImageView btnLogOutWhite;
    public StackPane SettingControllArea;
    public StackPane ModeControllArea;
    public Label lblMode;
    public VBox SearchBarVBox1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnTheme.setText("Light");
    }

    public void btnThemeOnAction(ActionEvent event) {
        if (btnTheme.isSelected()){
            lblMode.setText("Dark Mode");
            ModeControllArea.setVisible(true);
            ModeControllArea.setDisable(true);
            btnTheme.setText("Dark");
            DarkMode();
            SettingControllArea.setVisible(false);

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(1000));
            fadeTransition.setNode(ModeControllArea);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            Timeline timeline = new Timeline();
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(2000), actionEvent -> {
                ModeControllArea.setVisible(false);
                ModeControllArea.setDisable(false);
            });

            timeline.getKeyFrames().addAll(keyFrame1);
            timeline.playFromStart();
        }else {
            ModeControllArea.setVisible(true);
            ModeControllArea.setDisable(true);
            lblMode.setText("Light Mode");
            btnTheme.setText("Light");
            LightMode();
            SettingControllArea.setVisible(false);

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(1000));
            fadeTransition.setNode(ModeControllArea);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            Timeline timeline = new Timeline();
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(2000), actionEvent -> {
                ModeControllArea.setVisible(false);
                ModeControllArea.setDisable(false);
            });

            timeline.getKeyFrames().addAll(keyFrame1);
            timeline.playFromStart();
        }
    }

    void DarkMode(){
        btnDashboard.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManagePaddyStorage.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageOrder.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageEmployee.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageSupplier.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageBuyer.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageAssest.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageUser.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        btnManageProduct.setStyle("-fx-background-color: #34495e; -fx-text-fill: white");
        SearchBarVBox.setStyle("-fx-background-color: #34495e; -fx-border-color: white");
        SearchBarVBox1.setStyle("-fx-background-color: #34495e; -fx-border-color: white");
        SearchBar.setStyle("-fx-prompt-text-fill: White; -fx-text-fill: White");
        BackgroundImg.setStyle("-fx-opacity: 0.2");
        lblTime.setStyle("-fx-text-fill: White");
        lblDate.setStyle("-fx-text-fill: White");
        lblUserName.setStyle("-fx-text-fill: White");
        btnSettingWhite.setVisible(true);
        btnMassageWhite.setVisible(true);
        btnLogOutWhite.setVisible(true);
    }

    void LightMode(){
        btnDashboard.setStyle("-fx-background-color: #DADADA");
        btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageOrder.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageEmployee.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageSupplier.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageBuyer.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageAssest.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageUser.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        btnManageProduct.setStyle("-fx-background-color: #DADADA; -fx-text-fill: blabk");
        SearchBarVBox.setStyle("-fx-background-color:  #bdc3c7; -fx-border-color:  #1ABC9C");
        SearchBarVBox1.setStyle("-fx-background-color:  #bdc3c7; -fx-border-color:  #1ABC9C");
        SearchBar.setStyle("-fx-prompt-text-fill: blabk; -fx-text-fill: blabk");
        BackgroundImg.setStyle("-fx-opacity: 0.8");
        lblTime.setStyle("-fx-text-fill: blabk");
        lblDate.setStyle("-fx-text-fill: blabk");
        lblUserName.setStyle("-fx-text-fill: blabk");
        btnSettingWhite.setVisible(false);
        btnMassageWhite.setVisible(false);
        btnLogOutWhite.setVisible(false);
    }

    public void setAllButtons(Button btnDashboard, Button btnManagePaddyStorage, Button btnManageOrder, Button btnManageEmployee, Button btnManageSupplier, Button btnManageBuyer, Button btnManageAssest, Button btnManageUser, Button btnManageProduct, VBox searchBarVBox, JFXTextField searchBar, ImageView backgroundImg, Label lblTime, Label lblDate, Label lblUserName, ImageView btnSettingWhite, ImageView btnMassageWhite, ImageView btnLogOutWhite, StackPane settingControllArea, StackPane modeControllArea, Label lblMode, VBox searchBarVBox1) {
        this.btnDashboard=btnDashboard;
        this.btnManagePaddyStorage=btnManagePaddyStorage;
        this.btnManageOrder=btnManageOrder;
        this.btnManageEmployee=btnManageEmployee;
        this.btnManageSupplier=btnManageSupplier;
        this.btnManageBuyer=btnManageBuyer;
        this.btnManageAssest=btnManageAssest;
        this.btnManageUser=btnManageUser;
        this.btnManageProduct=btnManageProduct;
        this.SearchBarVBox=searchBarVBox;
        this.SearchBarVBox1=searchBarVBox1;
        this.SearchBar=searchBar;
        this.BackgroundImg=backgroundImg;
        this.lblTime=lblTime;
        this.lblDate=lblDate;
        this.lblUserName=lblUserName;
        this.btnSettingWhite=btnSettingWhite;
        this.btnMassageWhite=btnMassageWhite;
        this.btnLogOutWhite=btnLogOutWhite;
        this.SettingControllArea=settingControllArea;
        this.ModeControllArea=modeControllArea;
        this.lblMode=lblMode;
    }
}
