package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.EmployeeDTO;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException;
}
