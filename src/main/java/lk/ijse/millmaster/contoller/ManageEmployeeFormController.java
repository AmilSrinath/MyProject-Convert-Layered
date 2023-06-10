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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.EmployeeBO;
import lk.ijse.millmaster.dao.Custom.EmployeeDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.EmployeeDTO;
import lk.ijse.millmaster.dto.tm.AssestTM;
import lk.ijse.millmaster.dto.tm.EmployeeTM;
import lk.ijse.millmaster.model.EmployeeModel;
import lk.ijse.millmaster.util.Regex;
import lk.ijse.millmaster.util.TextFilds;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageEmployeeFormController implements Initializable {
    public Label lblUserName;
    public Label lblUserID;
    public TableView<EmployeeTM> tblEmployee;
    public TableColumn <?, ?>colUserID;
    public Button btnAttendance;
    public Label lblEmployeeID;
    public Label lblError;

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
    private JFXTextField txtSearchEmployee;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtContact;
    ObservableList<EmployeeTM> observableList;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLYOEE);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLYOEE);
    @SneakyThrows
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
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!employeeBO.deleteEmployee(txtID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
            }
            txtID.setText("");
            txtName.setText("");
            txtAddress.setText("");
            txtNIC.setText("");
            txtSalary.setText("");
            txtContact.setText("");
        }
        getAll();
        generateNextEmployeeID();
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

    private void generateNextEmployeeID() throws ClassNotFoundException, SQLException {
        String nextId = employeeBO.generateNewEmployeeID();
        lblEmployeeID.setText(nextId);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }
        String user = employeeDAO.searchByName(lblUserName.getText());
        lblUserID.setText(user);

        String id = lblEmployeeID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String contact = txtContact.getText();
        String uid = lblUserID.getText();

        if (employeeBO.addEmployee(new EmployeeDTO(id,name,address,nic,salary,contact,uid))){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
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

    private void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<EmployeeDTO> allEmployee = employeeBO.getAllEmployee();

        for (EmployeeDTO e : allEmployee) {
            observableList.add(new EmployeeTM(e.getId(), e.getName(),e.getAddress(),e.getNic(),e.getSalaryPerHour(),e.getContact(),e.getUserID()));
        }
        tblEmployee.setItems(observableList);
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

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Pleace Check TextFilds !").show();
            return;
        }

        String user = employeeDAO.searchByName(lblUserName.getText());
        lblUserID.setText(user);

        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String nic = txtNIC.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String contact = txtContact.getText();
        String userID = lblUserID.getText();

        if(employeeBO.updateEmployee(new EmployeeDTO(id,name,address,nic,salary,contact,userID))){
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
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
