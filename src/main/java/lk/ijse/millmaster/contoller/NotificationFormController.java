package lk.ijse.millmaster.contoller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class NotificationFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderActive();
    }

    @FXML
    private Label lblActiveOrders;
    public StackPane ControllArea;
    public StackPane NotificationControllArea;
    public Label lblWelCome;
    public ImageView imgMillmaster;

    @FXML
    void BtnOnMouseClicked(MouseEvent event) throws IOException, SQLException {
        OrderActive();
    }

    public void setAll(StackPane controllArea, StackPane notificationControllArea, Label lblWelCome, ImageView imgMillmaster) {
        this.ControllArea=controllArea;
        this.NotificationControllArea=notificationControllArea;
        this.imgMillmaster=imgMillmaster;
        this.lblWelCome=lblWelCome;
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
}
