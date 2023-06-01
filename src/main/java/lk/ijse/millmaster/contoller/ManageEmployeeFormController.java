package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.millmaster.dto.Employee;
import lk.ijse.millmaster.dto.User;
import lk.ijse.millmaster.dto.tm.BuyerTM;
import lk.ijse.millmaster.dto.tm.EmployeeTM;
import lk.ijse.millmaster.dto.tm.UserTM;
import lk.ijse.millmaster.model.EmployeeModel;
import lk.ijse.millmaster.model.SupplierModel;
import lk.ijse.millmaster.model.UserModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageEmployeeFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public Label lblUserName;
    public Label lblUserID;
    public TableView<EmployeeTM> tblEmployee;
    public TableColumn <?, ?>colUserID;
    public Button btnAttendance;
    public Label lblEmployeeID;
    public Label lblError;
    @FXML
    private AnchorPane ManageEmployeeForm;

    @FXML
    private TableColumn<?, ?> colEmpID;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpAddress;

    @FXML
    private TableColumn<?, ?> colEmpNIC;

    @FXML
    private TableColumn<?, ?> colEmpSalary;

    @FXML
    private TableColumn<?, ?> colEmpContact;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtSearchEmployee;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private JFXTextField txtContact;
    ObservableList<EmployeeTM> observableList;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        searchFilter();
        generateNextEmployeeID();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtSalary.setText("");
        txtContact.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM employee WHERE Emp_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        generateNextEmployeeID();
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtSalary.setText("");
        txtContact.setText("");
    }

    private void searchFilter(){
        FilteredList<EmployeeTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearchEmployee.setOnKeyReleased(e->{
            txtSearchEmployee.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super EmployeeTM>) employee->{
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (employee.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(employee.getId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(employee.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(employee.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(employee.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                });
            });

            SortedList<EmployeeTM> employee = new SortedList<>(filterData);
            employee.comparatorProperty().bind(tblEmployee.comparatorProperty());
            tblEmployee.setItems(employee);
        });
    }

    private void generateNextEmployeeID() {
        try {
            String nextId = EmployeeModel.generateNextOrderId();
            lblEmployeeID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }
        try {
            User user = UserModel.searchByName(lblUserName.getText());
            fillItemFields(user);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String salary = txtSalary.getText();
        String contact = txtContact.getText();


        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO employee(Emp_ID , Emp_Name, Emp_Address,Emp_NIC,Salary_Per_Hour,Emp_Contact_No,User_ID) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,lblEmployeeID.getText());
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setString(4,nic);
            pstm.setString(5,salary);
            pstm.setString(6,contact);
            pstm.setString(7,lblUserID.getText());

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Assest Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.CONFIRMATION, "This ID has been previously used!!").show();
            }
        }
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtSalary.setText("");
        txtContact.setText("");
        generateNextEmployeeID();
        getAll();
    }

    private void getAll() {
        try{
            observableList = FXCollections.observableArrayList();
            List<Employee> employees = EmployeeModel.getAll();

            for (Employee employee : employees){
                observableList.add(new EmployeeTM(
                        employee.getId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getNic(),
                        employee.getSalaryPerHour(),
                        employee.getContact(),
                        employee.getUserID()
                ));
            }
            tblEmployee.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
    }

    void setCellValueFactory(){
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmpNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory<>("salaryPerHour"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    private void fillItemFields(User employee) {
        lblUserID.setText(employee.getId());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        String salary = txtSalary.getText();
        String contact = txtContact.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE Employee SET Emp_Name = ?, Emp_Address = ?, Emp_NIC=?, Salary_Per_Hour=?, Emp_Contact_No=? WHERE Emp_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, nic);
            pstm.setString(4, salary);
            pstm.setString(5, contact);
            pstm.setString(6, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Buyer Updated!!").show();
            }
        }
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtSalary.setText("");
        txtContact.setText("");
        getAll();
        generateNextEmployeeID();
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        Integer index = tblEmployee.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtID.setText(colEmpID.getCellData(index).toString());
        txtName.setText(colEmpName.getCellData(index).toString());
        txtAddress.setText(colEmpAddress.getCellData(index).toString());
        txtNIC.setText(colEmpNIC.getCellData(index).toString());
        txtSalary.setText(colEmpSalary.getCellData(index).toString());
        txtContact.setText(colEmpContact.getCellData(index).toString());

        lblError.setVisible(false);
    }

    public void setLabe(String text) {
        lblUserName.setText(String.valueOf(text));
    }

    public void btnAttendanceOnAction(ActionEvent event) throws IOException {
        if (!txtID.getText().isEmpty()){
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/EmployeeAttendaceForm.fxml"));
            AnchorPane anchorPane = loader.load();
            EmployeeAttendaceFormController controller = loader.getController();
            controller.setData(txtID.getText(),txtName.getText(),txtSalary.getText());
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } else {
            lblError.setText("Please Select Row !!");
        }
    }


    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.NAME,txtName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.ADDRESS,txtAddress);
    }

    public void txtNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.LANKA_ID,txtNIC);
    }

    public void txtSalaryPerHourOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.DOUBLE,txtSalary);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFilds.PHONE,txtContact);
    }

    public boolean isValidated(){
        if (!Regex.setTextColor(TextFilds.NAME,txtName))return false;
        if (!Regex.setTextColor(TextFilds.ADDRESS,txtAddress))return false;
        if (!Regex.setTextColor(TextFilds.LANKA_ID,txtNIC))return false;
        if (!Regex.setTextColor(TextFilds.DOUBLE,txtSalary))return false;
        if (!Regex.setTextColor(TextFilds.PHONE,txtContact))return false;
        return true;
    }
}
