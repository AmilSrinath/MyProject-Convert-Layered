package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public String searchByName(String text) throws SQLException;
}
