package lk.ijse.millmaster.contoller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

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
    private AnchorPane DashboardPaddyStock;

    @FXML
    private Label lblPaddyStockCount;

    @FXML
    private Label lblPaddyStok;

    @FXML
    private AnchorPane DashboardStock1;

    @FXML
    private Label lblProduct5KgCount;

    @FXML
    private Label lblProduct5Kg;

    @FXML
    private AnchorPane DashboardStock11;

    @FXML
    private Label lblProduct10KgCount;

    @FXML
    private Label lblProduct10Kg;

    @FXML
    private AnchorPane DashboardStock111;

    @FXML
    private Label lblProduct25KgCount;

    @FXML
    private Label lblProduct25Kg;

    @FXML
    private Label lblDashboardUserName;

    @FXML
    private AnchorPane DashboardStock1112;

    @FXML
    private Label lblProductRedRawCount;

    @FXML
    private Label lblProductRedRaw;

    @FXML
    private Label lblProductRedRawKg;

    @FXML
    private AnchorPane DashboardStock12;

    @FXML
    private Label lblProductNaduCount;

    @FXML
    private Label lblProductNadu;

    @FXML
    private Label lblProductKg;

    @FXML
    private AnchorPane DashboardStock112;
    public Label lblProductCount1;
    public Label lblProduct1;
    public Label lblProductCount2;
    public Label lblProduct2;
    public Label lblProductCount3;
    public Label lblProduct3;
    public VBox SearchBarVBox;

    @FXML
    private Label lblProductRedNaduCount;

    @FXML
    private Label lblProductRedNadu;

    @FXML
    private Label lblProductRedNaduKg;

    @FXML
    private AnchorPane DashboardStock1111;

    @FXML
    private Label lblProductSambaCount;

    @FXML
    private Label lblProductSamba;

    @FXML
    private Label lblProductSambaKg;

    @FXML
    private AnchorPane DashboardStock13;

    @FXML
    private Label lblProductWhiteRawCount;

    @FXML
    private Label lblProductWhiteRaw;

    @FXML
    private Label lblProductWhiteRawKg;

    @FXML
    private AnchorPane DashboardStock113;

    @FXML
    private Label lblProductKeerisambaCount;

    @FXML
    private Label lblProductKeerisamba;

    @FXML
    private Label lblProductKeerisambaKg;

    @FXML
    private AnchorPane Nadu5Kg;

    @FXML
    private Label Nadu5KgC;

    @FXML
    private AnchorPane Product;

    @FXML
    private Label ProductC;

    @FXML
    private AnchorPane Nadu10Kg;

    @FXML
    private Label Nadu10KgC;

    @FXML
    private Label lblProduct5Kg1;

    @FXML
    private AnchorPane Nadu25Kg;

    @FXML
    private Label Nadu25KgC;

    @FXML
    private Label lblProduct5Kg11;

    @FXML
    private AnchorPane RedNadu5Kg;

    @FXML
    private Label RedNadu5KgC;

    @FXML
    private Label lblProduct5Kg2;

    @FXML
    private AnchorPane RedNadu10Kg;

    @FXML
    private Label RedNadu10KgC;

    @FXML
    private Label lblProduct5Kg12;

    @FXML
    private AnchorPane RedNadu25Kg;

    @FXML
    private Label RedNadu25KgC;

    @FXML
    private Label lblProduct5Kg111;

    @FXML
    private AnchorPane Samba5Kg;

    @FXML
    private Label Samba5KgC;

    @FXML
    private Label lblProduct5Kg3;

    @FXML
    private AnchorPane Samba10Kg;

    @FXML
    private Label Samba10KgC;

    @FXML
    private Label lblProduct5Kg13;

    @FXML
    private AnchorPane Samba25Kg;

    @FXML
    private Label Samba25KgC;

    @FXML
    private Label lblProduct5Kg112;

    @FXML
    private AnchorPane WhiteRaw5Kg;

    @FXML
    private Label WhiteRaw5KgC;

    @FXML
    private Label lblProduct5Kg21;

    @FXML
    private AnchorPane WhiteRaw10Kg;

    @FXML
    private Label WhiteRaw10KgC;

    @FXML
    private Label lblProduct5Kg121;

    @FXML
    private AnchorPane WhiteRaw25Kg;

    @FXML
    private Label WhiteRaw25KgC;

    @FXML
    private Label lblProduct5Kg1111;

    @FXML
    private AnchorPane Keerisamba5Kg;

    @FXML
    private Label Keerisamba5KgC;

    @FXML
    private Label lblProduct5Kg211;

    @FXML
    private AnchorPane Keerisamba10Kg;

    @FXML
    private Label Keerisamba10KgC;

    @FXML
    private Label lblProduct5Kg1211;

    @FXML
    private AnchorPane Keerisamba25Kg;

    @FXML
    private Label Keerisamba25KgC;

    @FXML
    private Label lblProduct5Kg11111;

    @FXML
    private AnchorPane RedRaw5Kg;

    @FXML
    private Label RedRaw5KgC;

    @FXML
    private Label lblProduct5Kg22;

    @FXML
    private AnchorPane RedRad10Kg;

    @FXML
    private Label RedRad10KgC;

    @FXML
    private Label lblProduct5Kg122;

    @FXML
    private AnchorPane RedRad25Kg;

    @FXML
    private Label RedRad25KgC;

    @FXML
    private Label lblProduct5Kg1112;

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
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT Count(Status)FROM orders WHERE Status='Complete'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblComC.setText(String.valueOf(sum));
        }
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
            lblActC.setText(String.valueOf(sum));
        }
    }

    private void getRedRawSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum5kg = 0;
            int sum10kg = 0;
            int sum25kg = 0;
            Statement sum5kgST = con.createStatement();
            Statement sum10kgST = con.createStatement();
            Statement sum25kgST = con.createStatement();

            ResultSet sum5kgRS = sum5kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Raw Rice 5Kg'");
            ResultSet sum10kgRS = sum10kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Raw Rice 10Kg'");
            ResultSet sum25kgRS = sum25kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Raw Rice 25Kg'");

            while (sum5kgRS.next()) {
                int c = sum5kgRS.getInt(1);
                sum5kg = sum5kg + c;
            }
            while (sum10kgRS.next()){
                int c1 = sum10kgRS.getInt(1);
                sum10kg = sum10kg + c1;
            }
            while (sum25kgRS.next()){
                int c1 = sum25kgRS.getInt(1);
                sum25kg = sum25kg + c1;
            }
            RedRaw5KgC.setText(String.valueOf(sum5kg));
            RedRad10KgC.setText(String.valueOf(sum10kg));
            RedRaw25KgC.setText(String.valueOf(sum25kg));
        }
    }

    private void getKeerisambaSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum5kg = 0;
            int sum10kg = 0;
            int sum25kg = 0;
            Statement sum5kgST = con.createStatement();
            Statement sum10kgST = con.createStatement();
            Statement sum25kgST = con.createStatement();

            ResultSet sum5kgRS = sum5kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Keerisamba 5Kg'");
            ResultSet sum10kgRS = sum10kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Keerisamba 10Kg'");
            ResultSet sum25kgRS = sum25kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Keerisamba 25Kg'");

            while (sum5kgRS.next()) {
                int c = sum5kgRS.getInt(1);
                sum5kg = sum5kg + c;
            }
            while (sum10kgRS.next()){
                int c1 = sum10kgRS.getInt(1);
                sum10kg = sum10kg + c1;
            }
            while (sum25kgRS.next()){
                int c1 = sum25kgRS.getInt(1);
                sum25kg = sum25kg + c1;
            }
            Keerisamba5KgC.setText(String.valueOf(sum5kg));
            Keerisamba10KgC.setText(String.valueOf(sum10kg));
            Keerisamba25KgC.setText(String.valueOf(sum25kg));
        }
    }

    private void getWhiteRawSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum5kg = 0;
            int sum10kg = 0;
            int sum25kg = 0;
            Statement sum5kgST = con.createStatement();
            Statement sum10kgST = con.createStatement();
            Statement sum25kgST = con.createStatement();

            ResultSet sum5kgRS = sum5kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='White Raw Rice 5Kg'");
            ResultSet sum10kgRS = sum10kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='White Raw Rice 10Kg'");
            ResultSet sum25kgRS = sum25kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='White Raw Rice 25Kg'");

            while (sum5kgRS.next()) {
                int c = sum5kgRS.getInt(1);
                sum5kg = sum5kg + c;
            }
            while (sum10kgRS.next()){
                int c1 = sum10kgRS.getInt(1);
                sum10kg = sum10kg + c1;
            }
            while (sum25kgRS.next()){
                int c1 = sum25kgRS.getInt(1);
                sum25kg = sum25kg + c1;
            }
            WhiteRaw5KgC.setText(String.valueOf(sum5kg));
            WhiteRaw10KgC.setText(String.valueOf(sum10kg));
            WhiteRaw25KgC.setText(String.valueOf(sum25kg));
        }
    }

    private void getSambaSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum5kg = 0;
            int sum10kg = 0;
            int sum25kg = 0;
            Statement sum5kgST = con.createStatement();
            Statement sum10kgST = con.createStatement();
            Statement sum25kgST = con.createStatement();

            ResultSet sum5kgRS = sum5kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Samba 5Kg'");
            ResultSet sum10kgRS = sum10kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Samba 10Kg'");
            ResultSet sum25kgRS = sum25kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Samba 25Kg'");

            while (sum5kgRS.next()) {
                int c = sum5kgRS.getInt(1);
                sum5kg = sum5kg + c;
            }
            while (sum10kgRS.next()){
                int c1 = sum10kgRS.getInt(1);
                sum10kg = sum10kg + c1;
            }
            while (sum25kgRS.next()){
                int c1 = sum25kgRS.getInt(1);
                sum25kg = sum25kg + c1;
            }
            Samba5KgC.setText(String.valueOf(sum5kg));
            Samba10KgC.setText(String.valueOf(sum10kg));
            Samba25KgC.setText(String.valueOf(sum25kg));
        }
    }

    private void getNaduSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum5kg = 0;
            int sum10kg = 0;
            int sum25kg = 0;
            Statement sum5kgST = con.createStatement();
            Statement sum10kgST = con.createStatement();
            Statement sum25kgST = con.createStatement();

            ResultSet sum5kgRS = sum5kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Nadu 5Kg'");
            ResultSet sum10kgRS = sum10kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Nadu 10Kg'");
            ResultSet sum25kgRS = sum25kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Nadu 25Kg'");

            while (sum5kgRS.next()) {
                int c = sum5kgRS.getInt(1);
                sum5kg = sum5kg + c;
            }
            while (sum10kgRS.next()){
                int c1 = sum10kgRS.getInt(1);
                sum10kg = sum10kg + c1;
            }
            while (sum25kgRS.next()){
                int c1 = sum25kgRS.getInt(1);
                sum25kg = sum25kg + c1;
            }
            Nadu5KgC.setText(String.valueOf(sum5kg));
            Nadu10KgC.setText(String.valueOf(sum10kg));
            Nadu25KgC.setText(String.valueOf(sum25kg));
        }
    }

    private void getRedNaduSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum5kg = 0;
            int sum10kg = 0;
            int sum25kg = 0;
            Statement sum5kgST = con.createStatement();
            Statement sum10kgST = con.createStatement();
            Statement sum25kgST = con.createStatement();

            ResultSet sum5kgRS = sum5kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Nadu 5Kg'");
            ResultSet sum10kgRS = sum10kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Nadu 10Kg'");
            ResultSet sum25kgRS = sum25kgST.executeQuery("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Nadu 25Kg'");

            while (sum5kgRS.next()) {
                int c = sum5kgRS.getInt(1);
                sum5kg = sum5kg + c;
            }
            while (sum10kgRS.next()){
                int c1 = sum10kgRS.getInt(1);
                sum10kg = sum10kg + c1;
            }
            while (sum25kgRS.next()){
                int c1 = sum25kgRS.getInt(1);
                sum25kg = sum25kg + c1;
            }
            RedNadu5KgC.setText(String.valueOf(sum5kg));
            RedNadu10KgC.setText(String.valueOf(sum10kg));
            RedNadu25KgC.setText(String.valueOf(sum25kg));
        }
    }

    //-----------------------------------Paddy-----------------------------------

    void getPaddyQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblPaddyStockCount.setText(String.valueOf(sum)+" Kg");
        }
    }

    void getPaddySambaQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Samba'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblProductSambaCount.setText(String.valueOf(sum));
        }
    }

    void getPaddyNaduQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Nadu'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblProductNaduCount.setText(String.valueOf(sum));
        }
    }

    void getPaddyRedNaduQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Red Nadu'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblProductRedNaduCount.setText(String.valueOf(sum));
        }
    }

    void getPaddyWhiteRawQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='White Raw Rice'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblProductWhiteRawCount.setText(String.valueOf(sum));
        }
    }

    void getPaddyKeerisambaQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Keerisamba'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblProductKeerisambaCount.setText(String.valueOf(sum));
        }
    }

    void getPaddyRedRowQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Red Raw Rice'");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            lblProductRedRawCount.setText(String.valueOf(sum));
        }
    }

    //-----------------------------------Product-----------------------------------

    void getProductQuntityColSum() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            int sum=0;
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SUM(Product_Quntity) FROM production");

            while (resultSet.next()){
                int c = resultSet.getInt(1);
                sum=sum+c;
            }
            ProductC.setText(String.valueOf(sum));
        }
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
    void OnMouseEntered7(MouseEvent event) {
        lblProduct5Kg.setStyle("-fx-text-fill: White");
        lblProduct5KgCount.setStyle("-fx-text-fill: White");
    }

    @FXML
    void OnMouseEntered8(MouseEvent event) {
        lblProduct10Kg.setStyle("-fx-text-fill: White");
        lblProduct10KgCount.setStyle("-fx-text-fill: White");
    }

    @FXML
    void OnMouseEntered9(MouseEvent event) {
        lblProduct25Kg.setStyle("-fx-text-fill: White");
        lblProduct25KgCount.setStyle("-fx-text-fill: White");
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

    @FXML
    void OnMouseExited7(MouseEvent event) {
        lblProduct5Kg.setStyle("-fx-text-fill: Black");
        lblProduct5KgCount.setStyle("-fx-text-fill: Black");
    }

    @FXML
    void OnMouseExited8(MouseEvent event) {
        lblProduct10Kg.setStyle("-fx-text-fill: Black");
        lblProduct10KgCount.setStyle("-fx-text-fill: Black");
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
