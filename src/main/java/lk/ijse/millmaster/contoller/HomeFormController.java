package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public ImageView btnClose;
    public VBox DashBoardButtonVBox;
    public AnchorPane DashBoardSidBar;
    public VBox UserImageVBox;
    public VBox SearchBarVBox;
    public VBox UserImageVBox1;
    public AnchorPane root2;
    public AnchorPane ManagePaddyStorageForm;
    public StackPane ControllArea;

    public Button btnManagePaddyStorage;
    public Button btnManageEmployee;
    public Button btnManageOrder;
    public Button btnManageSupplier;
    public Button btnManageBuyer;
    public Button btnManageAssest;
    public Button btnDashboard;
    public Button btnManageUser;
    public Button btnManageProduct;

    public Label lblDate;
    public Label lblTime;
    public Label lblUserName;
    public Label lblWelCome;
    public ImageView imgMillmaster;
    public StackPane SettingControllArea;
    public JFXTextField SearchBar;
    public ImageView BackgroundImg;
    public ImageView btnSettingWhite;
    public ImageView btnMassage;
    public ImageView btnMassageWhite;
    public ImageView btnLogOutWhite;
    public StackPane NotificationControllArea;
    public ImageView btnLogOut;
    public Label lblActiveOrders;
    public StackPane ModeControllArea;
    public Label lblMode;
    public VBox SearchBarVBox1;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllArea.setVisible(false);
        SettingControllArea.setVisible(false);
        NotificationControllArea.setVisible(false);
        lblDate.setText(String.valueOf((LocalDate.now())));
        TimeNow();

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/SettingForm.fxml"));
        AnchorPane anchorPane = loader.load();
        SettingFormController controller = loader.getController();
        controller.setAllButtons(btnDashboard,btnManagePaddyStorage,btnManageOrder,btnManageEmployee,btnManageSupplier,btnManageBuyer,btnManageAssest,btnManageUser,btnManageProduct,SearchBarVBox,SearchBar,BackgroundImg,lblTime,lblDate,lblUserName,btnSettingWhite,btnMassageWhite,btnLogOutWhite,SettingControllArea,ModeControllArea,lblMode,SearchBarVBox1);
        SettingControllArea.getChildren().removeAll();
        SettingControllArea.getChildren().setAll(anchorPane);

        btnSettingWhite.setVisible(false);
        btnMassageWhite.setVisible(false);
        btnLogOutWhite.setVisible(false);

        OrderActive();

        ModeControllArea.setVisible(false);
        ModeControllArea.setDisable(false);
    }

    void OrderActive() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT Count(Status)FROM orders WHERE Status='Active'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblActiveOrders.setText(String.valueOf(sum));
        }
    }

    public void btnCloseOnMouseClicked(MouseEvent mouseEvent) {
        System.exit(1);
    }

    public void setLabe(String user_name) {
        lblUserName.setText(user_name);
        if (!user_name.equalsIgnoreCase("Admin")){
            btnManageUser.setDisable(true);
        }
    }

    private void TimeNow(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!false){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    public void ImageAndLableContoller(){
        imgMillmaster.setStyle("-fx-opacity: 0.2");
        lblWelCome.setVisible(false);
        imgMillmaster.setLayoutY(100);
    }

    public void btnManagePaddyStorageOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManagePaddyStorageForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ManagePaddyStorageFormController controller = loader.getController();
        controller.setControllArea(ControllArea);
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnManageEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageEmployeeForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ManageEmployeeFormController controller = loader.getController();
        controller.setLabe(lblUserName.getText());
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnManageOrderOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageOrderForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ManageOrderFormController controller = loader.getController();
        controller.setControllArea(ControllArea,lblActiveOrders);
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnManageSupplierOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageSupplierForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnManageBuyerOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageBuyerForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ManageBuyerFormController controller = loader.getController();
        controller.setLabe(lblUserName.getText());
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnManageAssestOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageAssestForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ManageAssestFormController controller = loader.getController();
        controller.setLabe(lblUserName.getText());
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/DashboardForm.fxml"));
        AnchorPane anchorPane = loader.load();
        DashboardFormController controller = loader.getController();
        controller.setLabe(lblUserName.getText());
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnLogOutOnMouseCliced(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        root2.getScene().getWindow().hide();
    }

    public void btnManageUserOnAction(ActionEvent actionEvent) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageUserForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
            btnManageProduct.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
            btnManageProduct.setStyle("-fx-background-color: #DADADA;");
        }
    }

    public void btnManageProductOnAction(ActionEvent event) throws IOException {
        ControllArea.setVisible(true);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ManageProductForm.fxml"));
        AnchorPane anchorPane = loader.load();
        ControllArea.getChildren().removeAll();
        ControllArea.getChildren().setAll(anchorPane);
        ImageAndLableContoller();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(ControllArea);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        if (SearchBarVBox.getStyle()=="-fx-background-color: #34495e; -fx-border-color: white"){
            btnDashboard.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManagePaddyStorage.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageOrder.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageEmployee.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageSupplier.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageBuyer.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageAssest.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageUser.setStyle("-fx-background-color: #34495e;-fx-text-fill: white");
            btnManageProduct.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: white;-fx-border-width: 2px 0px 2px 2px;");
        }else {
            btnDashboard.setStyle("-fx-background-color: #DADADA;");
            btnManagePaddyStorage.setStyle("-fx-background-color: #DADADA;");
            btnManageOrder.setStyle("-fx-background-color: #DADADA;");
            btnManageEmployee.setStyle("-fx-background-color: #DADADA;");
            btnManageSupplier.setStyle("-fx-background-color: #DADADA;");
            btnManageBuyer.setStyle("-fx-background-color: #DADADA;");
            btnManageAssest.setStyle("-fx-background-color: #DADADA;");
            btnManageUser.setStyle("-fx-background-color: #DADADA;");
            btnManageProduct.setStyle("-fx-background-color: black; -fx-opacity: 0.7; -fx-text-fill: white;-fx-border-color: #fca404;-fx-border-width: 4px 0px 4px 4px;");
        }
    }

    public void SettingOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        SettingControllArea.setVisible(true);
        NotificationControllArea.setVisible(false);
    }

    public void SettingControllAreaOnMouseExited(MouseEvent mouseEvent) {
        SettingControllArea.setVisible(false);
    }

    public void btnMassageOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        NotificationControllArea.setVisible(true);
        SettingControllArea.setVisible(false);

        FXMLLoader loader1= new FXMLLoader(getClass().getResource("/view/NotificationForm.fxml"));
        AnchorPane anchorPane1 = loader1.load();
        NotificationFormController controller1 = loader1.getController();
        controller1.setAll(ControllArea,NotificationControllArea,lblWelCome,imgMillmaster);
        SettingControllArea.getChildren().removeAll();
        NotificationControllArea.getChildren().removeAll();
        NotificationControllArea.getChildren().setAll(anchorPane1);

    }

    public void NotificationControllAreaOnMouseExited(MouseEvent mouseEvent) throws IOException {
        NotificationControllArea.setVisible(false);
    }
}
