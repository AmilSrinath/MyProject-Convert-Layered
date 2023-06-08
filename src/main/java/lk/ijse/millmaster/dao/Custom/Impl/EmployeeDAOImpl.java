package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.EmployeeDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        List<Employee> employees = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("Emp_ID"),rst.getString("Emp_Name"),rst.getString("Emp_Address"),rst.getString("Emp_NIC"),rst.getDouble("Salary_Per_Hour"),rst.getString("Emp_Contact_No"),rst.getString("User_ID"));
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee(Emp_ID , Emp_Name, Emp_Address,Emp_NIC,Salary_Per_Hour,Emp_Contact_No,User_ID) VALUES(?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getNic(),
                entity.getSalaryPerHour(),
                entity.getContact(),
                entity.getUserID());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Employee SET Emp_Name = ?, Emp_Address = ?, Emp_NIC=?, Salary_Per_Hour=?, Emp_Contact_No=?, User_ID = ? WHERE Emp_ID = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getNic(),
                entity.getSalaryPerHour(),
                entity.getContact(),
                entity.getUserID(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE Emp_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Emp_ID FROM employee ORDER BY Emp_ID DESC LIMIT 1");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("E0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "E00"+id;
            }else {
                if (length < 3){
                    return "E0"+id;
                }else {
                    return "E"+id;
                }
            }
        } else {
            return "E001";
        }
    }

    @Override
    public String searchByName(String name) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM User WHERE User_Name = ?",name);
        if(rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
