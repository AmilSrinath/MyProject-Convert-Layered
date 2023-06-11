package lk.ijse.millmaster.contoller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import lk.ijse.millmaster.dao.Custom.NotificationDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NotificationFormController implements Initializable {
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

    NotificationDAO notificationDAO = (NotificationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.NOTIFICATION);

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
        lblActiveOrders.setText(String.valueOf(notificationDAO.OrderActive()));
    }
}
