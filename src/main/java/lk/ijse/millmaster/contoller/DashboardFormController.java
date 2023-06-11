package lk.ijse.millmaster.contoller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.millmaster.dao.Custom.DashboardDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public Label lblRedRaw25Kg;
    public Label lblRedRaw10Kg;
    public Label lblRedRaw5Kg;
    public Label lblKeerisamba25Kg;
    public Label lblKeerisamba10Kg;
    public Label lblKeerisamba5Kg;
    public Label lblWhiteRaw25Kg;
    public Label lblWhiteRaw10Kg;
    public Label lblWhiteRaw5Kg;
    public Label lblSamba25Kg;
    public Label lblSamba10Kg;
    public Label lblSamba5Kg;
    public Label lblRedNadu25Kg;
    public Label lblRedNadu10Kg;
    public Label lblRedNadu5Kg;
    public Label lblNadu25Kg;
    public Label lblNadu10Kg;
    public Label lblNadu5Kg;
    public Label RedRaw25KgC;
    public Label lblProduct;
    public Label lblComC;
    public Label lblComOrder;
    public Label lblActOrder;
    public Label lblActC;
    public AnchorPane OrderAct;
    public AnchorPane OrderCom;

    @FXML
    private Label lblPaddyStockCount;
    @FXML
    private Label lblPaddyStok;
    @FXML
    private Label lblDashboardUserName;
    @FXML
    private Label lblProductRedRawCount;
    @FXML
    private Label lblProductRedRaw;
    @FXML
    private Label lblProductRedRawKg;
    @FXML
    private Label lblProductNaduCount;
    @FXML
    private Label lblProductNadu;
    @FXML
    private Label lblProductKg;
    public VBox SearchBarVBox;
    @FXML
    private Label lblProductRedNaduCount;
    @FXML
    private Label lblProductRedNadu;
    @FXML
    private Label lblProductRedNaduKg;
    @FXML
    private Label lblProductSambaCount;
    @FXML
    private Label lblProductSamba;
    @FXML
    private Label lblProductSambaKg;
    @FXML
    private Label lblProductWhiteRawCount;
    @FXML
    private Label lblProductWhiteRaw;
    @FXML
    private Label lblProductWhiteRawKg;
    @FXML
    private Label lblProductKeerisambaCount;
    @FXML
    private Label lblProductKeerisamba;
    @FXML
    private Label lblProductKeerisambaKg;
    @FXML
    private Label Nadu5KgC;
    @FXML
    private Label ProductC;
    @FXML
    private Label Nadu10KgC;
    @FXML
    private Label Nadu25KgC;
    @FXML
    private Label RedNadu5KgC;
    @FXML
    private Label RedNadu10KgC;
    @FXML
    private Label RedNadu25KgC;
    @FXML
    private Label Samba5KgC;
    @FXML
    private Label Samba10KgC;
    @FXML
    private Label Samba25KgC;
    @FXML
    private Label WhiteRaw5KgC;
    @FXML
    private Label WhiteRaw10KgC;
    @FXML
    private Label WhiteRaw25KgC;
    @FXML
    private Label Keerisamba5KgC;
    @FXML
    private Label Keerisamba10KgC;
    @FXML
    private Label Keerisamba25KgC;
    @FXML
    private Label RedRaw5KgC;
    @FXML
    private Label RedRad10KgC;

    DashboardDAO dashboardDAO = (DashboardDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DASHBOARD);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPaddyQuntityColSum();

        //-----------------------------------Paddy-----------------------------------
        getPaddySambaQuntityColSum();
        getPaddyNaduQuntityColSum();
        getPaddyRedNaduQuntityColSum();
        getPaddyWhiteRawQuntityColSum();
        getPaddyKeerisambaQuntityColSum();
        getPaddyRedRowQuntityColSum();

        //-----------------------------------Product-----------------------------------
        getProductQuntityColSum();

        getNaduSum();
        getRedNaduSum();
        getSambaSum();
        getWhiteRawSum();
        getKeerisambaSum();
        getRedRawSum();

        OrderComplete();
        OrderActive();
    }
    void OrderComplete() throws SQLException {
        lblComC.setText(String.valueOf(dashboardDAO.OrderComplete()));
    }

    void OrderActive() throws SQLException {
        lblActC.setText(String.valueOf(dashboardDAO.OrderActive()));
    }

    private void getRedRawSum() throws SQLException {
        RedRaw5KgC.setText(String.valueOf(dashboardDAO.getRedRawSum5kg()));
        RedRad10KgC.setText(String.valueOf(dashboardDAO.getRedRawSum10kg()));
        RedRaw25KgC.setText(String.valueOf(dashboardDAO.getRedRawSum25kg()));
    }

    private void getKeerisambaSum() throws SQLException {
        Keerisamba5KgC.setText(String.valueOf(dashboardDAO.getKeerisambaSum5kg()));
        Keerisamba10KgC.setText(String.valueOf(dashboardDAO.getKeerisambaSum10kg()));
        Keerisamba25KgC.setText(String.valueOf(dashboardDAO.getKeerisambaSum25kg()));
    }

    private void getWhiteRawSum() throws SQLException {
        WhiteRaw5KgC.setText(String.valueOf(dashboardDAO.getWhiteRawSum5kg()));
        WhiteRaw10KgC.setText(String.valueOf(dashboardDAO.getWhiteRawSum10kg()));
        WhiteRaw25KgC.setText(String.valueOf(dashboardDAO.getWhiteRawSum25kg()));
    }

    private void getSambaSum() throws SQLException {
        Samba5KgC.setText(String.valueOf(dashboardDAO.getSambaSum5kg()));
        Samba10KgC.setText(String.valueOf(dashboardDAO.getSambaSum10kg()));
        Samba25KgC.setText(String.valueOf(dashboardDAO.getSambaSum25kg()));
    }

    private void getNaduSum() throws SQLException {
        Nadu5KgC.setText(String.valueOf(dashboardDAO.getNaduSum5kg()));
        Nadu10KgC.setText(String.valueOf(dashboardDAO.getNaduSum10kg()));
        Nadu25KgC.setText(String.valueOf(dashboardDAO.getNaduSum25kg()));
    }

    private void getRedNaduSum() throws SQLException {
        RedNadu5KgC.setText(String.valueOf(dashboardDAO.getRedNaduSum5kg()));
        RedNadu10KgC.setText(String.valueOf(dashboardDAO.getRedNaduSum10kg()));
        RedNadu25KgC.setText(String.valueOf(dashboardDAO.getRedNaduSum25kg()));
    }

    //-----------------------------------Paddy-----------------------------------

    void getPaddyQuntityColSum() throws SQLException {
        lblPaddyStockCount.setText(dashboardDAO.getPaddyQuntityColSum()+" Kg");
    }

    void getPaddySambaQuntityColSum() throws SQLException {
        lblProductSambaCount.setText(String.valueOf(dashboardDAO.getPaddySambaQuntityColSum()));
    }

    void getPaddyNaduQuntityColSum() throws SQLException {
        lblProductNaduCount.setText(String.valueOf(dashboardDAO.getPaddyNaduQuntityColSum()));
    }

    void getPaddyRedNaduQuntityColSum() throws SQLException {
        lblProductRedNaduCount.setText(String.valueOf(dashboardDAO.getPaddyRedNaduQuntityColSum()));
    }

    void getPaddyWhiteRawQuntityColSum() throws SQLException {
        lblProductWhiteRawCount.setText(String.valueOf(dashboardDAO.getPaddyWhiteRawQuntityColSum()));
    }

    void getPaddyKeerisambaQuntityColSum() throws SQLException {
        lblProductKeerisambaCount.setText(String.valueOf(dashboardDAO.getPaddyKeerisambaQuntityColSum()));
    }

    void getPaddyRedRowQuntityColSum() throws SQLException {
        lblProductRedRawCount.setText(String.valueOf(dashboardDAO.getPaddyRedRowQuntityColSum()));
    }

    //-----------------------------------Product-----------------------------------

    void getProductQuntityColSum() throws SQLException {
        ProductC.setText(String.valueOf(dashboardDAO.getProductQuntityColSum()));
    }

    public void OnMouseEntered(MouseEvent mouseEvent) {
        lblPaddyStok.setStyle("-fx-text-fill: White");
        lblPaddyStockCount.setStyle("-fx-text-fill: White");
    }

    public void OnMouseExited(MouseEvent mouseEvent) {
        lblPaddyStok.setStyle("-fx-text-fill: Black");
        lblPaddyStockCount.setStyle("-fx-text-fill: Black");
    }

    public void OnMouseEntered1(MouseEvent mouseEvent) {
        lblProductNadu.setStyle("-fx-text-fill: White");
        lblProductNaduCount.setStyle("-fx-text-fill: White");
        lblProductKg.setStyle("-fx-text-fill: White");
    }

    public void OnMouseExited1(MouseEvent mouseEvent) {
        lblProductNadu.setStyle("-fx-text-fill: Black");
        lblProductNaduCount.setStyle("-fx-text-fill: Black");
        lblProductKg.setStyle("-fx-text-fill: Black");
    }

    @FXML
    void OnMouseEntered2(MouseEvent event) {
        lblProductRedNadu.setStyle("-fx-text-fill: White");
        lblProductRedNaduCount.setStyle("-fx-text-fill: White");
        lblProductRedNaduKg.setStyle("-fx-text-fill: White");
    }

    @FXML
    void OnMouseEntered3(MouseEvent event) {
        lblProductSamba.setStyle("-fx-text-fill: White");
        lblProductSambaCount.setStyle("-fx-text-fill: White");
        lblProductSambaKg.setStyle("-fx-text-fill: White");
    }

    @FXML
    void OnMouseEntered4(MouseEvent event) {
        lblProductWhiteRaw.setStyle("-fx-text-fill: White");
        lblProductWhiteRawCount.setStyle("-fx-text-fill: White");
        lblProductWhiteRawKg.setStyle("-fx-text-fill: White");
    }

    @FXML
    void OnMouseEntered5(MouseEvent event) {
        lblProductKeerisamba.setStyle("-fx-text-fill: White");
        lblProductKeerisambaCount.setStyle("-fx-text-fill: White");
        lblProductKeerisambaKg.setStyle("-fx-text-fill: White");
    }

    @FXML
    void OnMouseEntered6(MouseEvent event) {
        lblProductRedRaw.setStyle("-fx-text-fill: White");
        lblProductRedRawCount.setStyle("-fx-text-fill: White");
        lblProductRedRawKg.setStyle("-fx-text-fill: White");
    }


    @FXML
    void OnMouseExited2(MouseEvent event) {
        lblProductRedNadu.setStyle("-fx-text-fill: Black");
        lblProductRedNaduCount.setStyle("-fx-text-fill: Black");
        lblProductRedNaduKg.setStyle("-fx-text-fill: Black");
    }

    @FXML
    void OnMouseExited3(MouseEvent event) {
        lblProductSamba.setStyle("-fx-text-fill: Black");
        lblProductSambaCount.setStyle("-fx-text-fill: Black");
        lblProductSambaKg.setStyle("-fx-text-fill: Black");
    }

    @FXML
    void OnMouseExited4(MouseEvent event) {
        lblProductWhiteRaw.setStyle("-fx-text-fill: Black");
        lblProductWhiteRawCount.setStyle("-fx-text-fill: Black");
        lblProductWhiteRawKg.setStyle("-fx-text-fill: Black");
    }

    @FXML
    void OnMouseExited5(MouseEvent event) {
        lblProductKeerisamba.setStyle("-fx-text-fill: Black");
        lblProductKeerisambaCount.setStyle("-fx-text-fill: Black");
        lblProductKeerisambaKg.setStyle("-fx-text-fill: Black");
    }

    @FXML
    void OnMouseExited6(MouseEvent event) {
        lblProductRedRaw.setStyle("-fx-text-fill: Black");
        lblProductRedRawCount.setStyle("-fx-text-fill: Black");
        lblProductRedRawKg.setStyle("-fx-text-fill: Black");
    }

    public void setLabe(String text) {
        lblDashboardUserName.setText(text+"!");
    }

    public void Nadu5KgOnMouseEnterd(MouseEvent mouseEvent) {
        lblNadu5Kg.setStyle("-fx-text-fill: White");
        Nadu5KgC.setStyle("-fx-text-fill: White");
    }

    public void Nadu5KgOnMouseExited(MouseEvent mouseEvent) {
        lblNadu5Kg.setStyle("-fx-text-fill: Black");
        Nadu5KgC.setStyle("-fx-text-fill: Black");
    }

    public void Nadu10KgOnMouseEnterd(MouseEvent mouseEvent) {
        lblNadu10Kg.setStyle("-fx-text-fill: White");
        Nadu10KgC.setStyle("-fx-text-fill: White");
    }

    public void Nadu10KgOnMouseExited(MouseEvent mouseEvent) {
        lblNadu10Kg.setStyle("-fx-text-fill: Black");
        Nadu10KgC.setStyle("-fx-text-fill: Black");
    }

    public void Nadu25KgOnMouseEnterd(MouseEvent mouseEvent) {
        lblNadu25Kg.setStyle("-fx-text-fill: White");
        Nadu25KgC.setStyle("-fx-text-fill: White");
    }

    public void Nadu25KgOnMouseExited(MouseEvent mouseEvent) {
        lblNadu25Kg.setStyle("-fx-text-fill: Black");
        Nadu25KgC.setStyle("-fx-text-fill: Black");
    }

    public void RedNadu5KgOnMouseEnterd(MouseEvent mouseEvent) {
        lblRedNadu5Kg.setStyle("-fx-text-fill: White");
        RedNadu5KgC.setStyle("-fx-text-fill: White");
    }

    public void RedNadu5KgOnMouseExited(MouseEvent mouseEvent) {
        lblRedNadu5Kg.setStyle("-fx-text-fill: Black");
        RedNadu5KgC.setStyle("-fx-text-fill: Black");
    }

    public void RedNadu10KgOnMouseEntered(MouseEvent mouseEvent) {
        lblRedNadu10Kg.setStyle("-fx-text-fill: White");
        RedNadu10KgC.setStyle("-fx-text-fill: White");
    }

    public void RedNadu10KgOnMouseExited(MouseEvent mouseEvent) {
        lblRedNadu10Kg.setStyle("-fx-text-fill: Black");
        RedNadu10KgC.setStyle("-fx-text-fill: Black");
    }

    public void RedNadu25KgOnMouseEntered(MouseEvent mouseEvent) {
        lblRedNadu25Kg.setStyle("-fx-text-fill: White");
        RedNadu25KgC.setStyle("-fx-text-fill: White");
    }

    public void RedNadu25KgOnMouseExited(MouseEvent mouseEvent) {
        lblRedNadu25Kg.setStyle("-fx-text-fill: Black");
        RedNadu25KgC.setStyle("-fx-text-fill: Black");
    }

    public void Samba5KgOnMouseEntered(MouseEvent mouseEvent) {
        lblSamba5Kg.setStyle("-fx-text-fill: White");
        Samba5KgC.setStyle("-fx-text-fill: White");
    }

    public void Samba5KgOnMouseExited(MouseEvent mouseEvent) {
        lblSamba5Kg.setStyle("-fx-text-fill: Black");
        Samba5KgC.setStyle("-fx-text-fill: Black");
    }

    public void Samba10KgOnMouseEntered(MouseEvent mouseEvent) {
        lblSamba10Kg.setStyle("-fx-text-fill: White");
        Samba10KgC.setStyle("-fx-text-fill: White");
    }

    public void Samba10KgOnMouseExited(MouseEvent mouseEvent) {
        lblSamba10Kg.setStyle("-fx-text-fill: Black");
        Samba10KgC.setStyle("-fx-text-fill: Black");
    }

    public void Samba25KgOnMouseEntered(MouseEvent mouseEvent) {
        lblSamba25Kg.setStyle("-fx-text-fill: White");
        Samba25KgC.setStyle("-fx-text-fill: White");
    }

    public void Samba25KgOnMouseExited(MouseEvent mouseEvent) {
        lblSamba25Kg.setStyle("-fx-text-fill: Black");
        Samba25KgC.setStyle("-fx-text-fill: Black");
    }

    public void WhiteRaw5KgOnMouseEntered(MouseEvent mouseEvent) {
        lblWhiteRaw5Kg.setStyle("-fx-text-fill: White");
        WhiteRaw5KgC.setStyle("-fx-text-fill: White");
    }

    public void WhiteRaw5KgOnMouseExited(MouseEvent mouseEvent) {
        lblWhiteRaw5Kg.setStyle("-fx-text-fill: Black");
        WhiteRaw5KgC.setStyle("-fx-text-fill: Black");
    }

    public void WhiteRaw10KgOnMouseEntered(MouseEvent mouseEvent) {
        lblWhiteRaw10Kg.setStyle("-fx-text-fill: White");
        WhiteRaw10KgC.setStyle("-fx-text-fill: White");
    }

    public void WhiteRaw10KgOnMouseExited(MouseEvent mouseEvent) {
        lblWhiteRaw10Kg.setStyle("-fx-text-fill: Black");
        WhiteRaw10KgC.setStyle("-fx-text-fill: Black");
    }

    public void WhiteRaw25KgOnMouseEntered(MouseEvent mouseEvent) {
        lblWhiteRaw25Kg.setStyle("-fx-text-fill: White");
        WhiteRaw25KgC.setStyle("-fx-text-fill: White");
    }

    public void WhiteRaw25KgOnMouseExited(MouseEvent mouseEvent) {
        lblWhiteRaw25Kg.setStyle("-fx-text-fill: Black");
        WhiteRaw25KgC.setStyle("-fx-text-fill: Black");
    }

    public void Keerisamba5KgOnMouseEntered(MouseEvent mouseEvent) {
        lblKeerisamba5Kg.setStyle("-fx-text-fill: White");
        Keerisamba5KgC.setStyle("-fx-text-fill: White");
    }

    public void Keerisamba5KgOnMouseExited(MouseEvent mouseEvent) {
        lblKeerisamba5Kg.setStyle("-fx-text-fill: Black");
        Keerisamba5KgC.setStyle("-fx-text-fill: Black");
    }

    public void Keerisamba10KgOnMouseEntered(MouseEvent mouseEvent) {
        lblKeerisamba10Kg.setStyle("-fx-text-fill: White");
        Keerisamba10KgC.setStyle("-fx-text-fill: White");
    }

    public void Keerisamba10KgOnMouseExited(MouseEvent mouseEvent) {
        lblKeerisamba10Kg.setStyle("-fx-text-fill: Black");
        Keerisamba10KgC.setStyle("-fx-text-fill: Black");
    }

    public void Keerisamba25KgOnMouseEntered(MouseEvent mouseEvent) {
        lblKeerisamba25Kg.setStyle("-fx-text-fill: White");
        Keerisamba25KgC.setStyle("-fx-text-fill: White");
    }

    public void Keerisamba25KgOnMouseExited(MouseEvent mouseEvent) {
        lblKeerisamba25Kg.setStyle("-fx-text-fill: Black");
        Keerisamba25KgC.setStyle("-fx-text-fill: Black");
    }

    public void RedRaw5KgOnMouseEntered(MouseEvent mouseEvent) {
        lblRedRaw5Kg.setStyle("-fx-text-fill: White");
        RedRaw5KgC.setStyle("-fx-text-fill: White");
    }

    public void RedRaw5KgOnMouseExited(MouseEvent mouseEvent) {
        lblRedRaw5Kg.setStyle("-fx-text-fill: Black");
        RedRaw5KgC.setStyle("-fx-text-fill: Black");
    }

    public void RedRaw10KgOnMouseEntered(MouseEvent mouseEvent) {
        lblRedRaw10Kg.setStyle("-fx-text-fill: White");
        RedRad10KgC.setStyle("-fx-text-fill: White");
    }

    public void RedRaw10KgOnMouseExited(MouseEvent mouseEvent) {
        lblRedRaw10Kg.setStyle("-fx-text-fill: Black");
        RedRad10KgC.setStyle("-fx-text-fill: Black");
    }

    public void RedRaw25KgOnMouseEntered(MouseEvent mouseEvent) {
        lblRedRaw25Kg.setStyle("-fx-text-fill: White");
        RedRaw25KgC.setStyle("-fx-text-fill: White");
    }

    public void RedRaw25KgOnMouseExited(MouseEvent mouseEvent) {
        lblRedRaw25Kg.setStyle("-fx-text-fill: Black");
        RedRaw25KgC.setStyle("-fx-text-fill: Black");
    }

    public void ProductOnMouseEnterd(MouseEvent mouseEvent) {
        lblProduct.setStyle("-fx-text-fill: White");
        ProductC.setStyle("-fx-text-fill: White");
    }

    public void ProductOnMouseExited(MouseEvent mouseEvent) {
        lblProduct.setStyle("-fx-text-fill: Black");
        ProductC.setStyle("-fx-text-fill: Black");
    }

    public void OrderCompleteOnMouseEnterd(MouseEvent mouseEvent) {
        lblComOrder.setStyle("-fx-text-fill: White");
        lblComC.setStyle("-fx-text-fill: White");
    }

    public void OrderActiveOnMouseEnterd(MouseEvent mouseEvent) {
        lblActOrder.setStyle("-fx-text-fill: White");
        lblActC.setStyle("-fx-text-fill: White");
    }

    public void OrderCompleteOnMouseExited(MouseEvent mouseEvent) {
        lblComOrder.setStyle("-fx-text-fill: Black");
        lblComC.setStyle("-fx-text-fill: Black");
    }

    public void OrderActiveOnMouseExited(MouseEvent mouseEvent) {
        lblActOrder.setStyle("-fx-text-fill: Black");
        lblActC.setStyle("-fx-text-fill: Black");
    }
}
