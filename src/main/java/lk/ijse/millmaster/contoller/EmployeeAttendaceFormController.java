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
import javafx.scene.layout.VBox;
import lk.ijse.millmaster.dto.Attendance;
import lk.ijse.millmaster.dto.Employee;
import lk.ijse.millmaster.dto.tm.AttendanceTM;
import lk.ijse.millmaster.model.AttendanceModel;
import lk.ijse.millmaster.model.EmployeeModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeAttendaceFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Millmaster";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "12345678");
    }

    public ComboBox<Integer> comWorkingHours;
    public Button btnCalculate;
    public TableColumn <?,?>colEmpID;
    public TableColumn<?,?> colSalary;
    public ComboBox <String>comEmp_ID;
    public ImageView btnClose;
    public AnchorPane root;
    public Label lblEmpSalaryPerHour;
    public Label lblAttendaceID;
    public Label lblEmployeeSalaryPerHour;
    public Label lblEmployeeName;
    public Label lblEmpID;
    @FXML
    private AnchorPane ManageEmployeeForm;

    @FXML
    private TableView<AttendanceTM> tblEmployee;

    @FXML
    private TableColumn<?, ?> colAttenID;

    @FXML
    private TableColumn<?, ?> colWorkingHour;

    @FXML
    private JFXTextField txtID;

    @FXML
    private VBox SearchBarVBox;

    @FXML
    private JFXTextField txtSearchAttendace;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;
    ObservableList<AttendanceTM> observableList;
    String lblUserName;
    String ID;
    String Name;
    String Salary;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> list = FXCollections.observableArrayList(4, 6, 8, 10, 12);
        comWorkingHours.setItems(list);
        setCellValueFactory();
        getAll();
        generateNextAttendenceID();
    }

    private void generateNextAttendenceID() {
        try {
            String nextId = AttendanceModel.generateNextOrderId();
            lblAttendaceID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        colEmpID.setText("");
        colWorkingHour.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try (Connection con = DriverManager.getConnection(URL, props)) {
                String sql = "DELETE FROM attendance WHERE Attendance_ID = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, txtID.getText());
                System.out.println(txtID.getText());
                pstm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        getAll();
        generateNextAttendenceID();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        Integer hours = comWorkingHours.getValue();
        Double salary = Double.valueOf(lblEmployeeSalaryPerHour.getText());
        Double S = hours * salary;

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE attendance SET Working_Hour = ?, Salary=? WHERE Attendance_ID = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, hours);
            pstm.setDouble(2, S);
            pstm.setString(3, txtID.getText());

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated!!").show();
            }
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

    public void btnCalculateOnAction(ActionEvent event) throws SQLException {
        Object selectedItem = comWorkingHours.getSelectionModel().getSelectedItem();
        int hour = (Integer) selectedItem;
        Double salaryPerHour = Double.valueOf(lblEmployeeSalaryPerHour.getText());
        Double salary = salaryPerHour * hour;

        try(Connection con = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO attendance(Attendance_ID,Working_Hour,Emp_ID,Salary) VALUES(?,?,?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,lblAttendaceID.getText());
            pstm.setInt(2,hour);
            pstm.setString(3, lblEmpID.getText());
            pstm.setDouble(4,salary);

            try {
                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Attendance Added !!").show();
                }
            }catch (Exception ex){
                new Alert(Alert.AlertType.ERROR,"This ID has been previously used!!").show();
            }
        }
        colEmpID.setText("");
        colWorkingHour.setText("");
        getAll();
        generateNextAttendenceID();
    }

    private void fillItemFields(Employee employee) {
        lblEmpSalaryPerHour.setText(String.valueOf(employee.getSalaryPerHour()));
    }

    void getAll() throws SQLException {
        try{
            observableList = FXCollections.observableArrayList();
            List<Attendance> buyerList = AttendanceModel.getAll();

            for ( Attendance attendance: buyerList){
                observableList.add(new AttendanceTM(
                        attendance.getId(),
                        attendance.getHour(),
                        attendance.getEmp_id(),
                        attendance.getSalary()
                ));
            }

            tblEmployee.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query Error!!").show();
        }
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
