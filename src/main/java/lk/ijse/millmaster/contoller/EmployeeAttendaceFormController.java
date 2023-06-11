package lk.ijse.millmaster.contoller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.millmaster.bo.BOFactory;
import lk.ijse.millmaster.bo.Custom.AttendanceBO;
import lk.ijse.millmaster.dto.AttendanceDTO;
import lk.ijse.millmaster.dto.tm.AttendanceTM;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeAttendaceFormController implements Initializable {
    public ComboBox<Integer> comWorkingHours;
    public Button btnCalculate;
    public TableColumn <?,?>colEmpID;
    public TableColumn<?,?> colSalary;
    public ImageView btnClose;
    public AnchorPane root;
    public Label lblAttendaceID;
    public Label lblEmployeeSalaryPerHour;
    public Label lblEmployeeName;
    public Label lblEmpID;

    @FXML
    private TableView<AttendanceTM> tblEmployee;

    @FXML
    private TableColumn<?, ?> colAttenID;

    @FXML
    private TableColumn<?, ?> colWorkingHour;

    @FXML
    private JFXTextField txtID;
    ObservableList<AttendanceTM> observableList;
    String ID;
    String Name;
    String Salary;


    AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ATTENDANCE);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> list = FXCollections.observableArrayList(4, 6, 8, 10, 12);
        comWorkingHours.setItems(list);
        setCellValueFactory();
        getAll();
        generateNextAttendenceID();
    }

    private void generateNextAttendenceID() throws SQLException, ClassNotFoundException {
        String nextId = attendanceBO.generateNewAttendanceID();
        lblAttendaceID.setText(nextId);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        colEmpID.setText("");
        colWorkingHour.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if(!attendanceBO.deleteAttendance(txtID.getText())){
                new Alert(Alert.AlertType.ERROR,"SQL Error").show();
            }
        }
        getAll();
        generateNextAttendenceID();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Integer hours = comWorkingHours.getValue();
        Double salary = Double.valueOf(lblEmployeeSalaryPerHour.getText());
        Double S = hours * salary;
        String id = txtID.getText();

        if(attendanceBO.updateAttendance(new AttendanceDTO(id,hours,null,S))){
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Salary Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
        getAll();
        generateNextAttendenceID();
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        Integer index = tblEmployee.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtID.setText(colAttenID.getCellData(index).toString());
        comWorkingHours.setValue(Integer.valueOf(colWorkingHour.getCellData(index).toString()));
    }

    public void btnCloseOnMouseClicked(MouseEvent mouseEvent) {
        root.getScene().getWindow().hide();
    }

    public void btnCalculateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Object selectedItem = comWorkingHours.getSelectionModel().getSelectedItem();
        int hour = (Integer) selectedItem;
        Double salaryPerHour = Double.valueOf(lblEmployeeSalaryPerHour.getText());
        Double salary = salaryPerHour * hour;
        String id = lblAttendaceID.getText();
        String eid = lblEmpID.getText();

        if(attendanceBO.addAttendance(new AttendanceDTO(id,hour,eid,salary))){
            new Alert(Alert.AlertType.CONFIRMATION,"Attendance Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
        }

        colEmpID.setText("");
        colWorkingHour.setText("");
        getAll();
        generateNextAttendenceID();
    }

    void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<AttendanceDTO> allCustomers = attendanceBO.getAllAttendance();

        for (AttendanceDTO a : allCustomers) {
            observableList.add(new AttendanceTM(a.getId(), a.getHour(),a.getEmp_id(),a.getSalary()));
        }
        tblEmployee.setItems(observableList);
    }

    void setCellValueFactory() {
        colAttenID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colWorkingHour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    public void setData(String id, String name, String salary) {
        this.ID=id;
        this.Name=name;
        this.Salary=salary;

        lblEmpID.setText(id);
        lblEmployeeName.setText(name);
        lblEmployeeSalaryPerHour.setText(salary);
    }
}
