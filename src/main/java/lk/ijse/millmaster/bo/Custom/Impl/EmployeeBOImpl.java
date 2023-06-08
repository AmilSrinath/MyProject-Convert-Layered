package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.EmployeeBO;
import lk.ijse.millmaster.dao.Custom.EmployeeDAO;
import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.EmployeeDTO;
import lk.ijse.millmaster.entity.Assest;
import lk.ijse.millmaster.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLYOEE);
    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        List<EmployeeDTO> arrayList= new ArrayList<>();
        List<Employee> employees = employeeDAO.getAll();
        for (Employee e : employees) {
            arrayList.add(new EmployeeDTO(e.getId(),e.getName(),e.getAddress(),e.getNic(),e.getSalaryPerHour(),e.getContact(),e.getUserID()));
        }
        return arrayList;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getNic(),dto.getSalaryPerHour(),dto.getContact(),dto.getUserID()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getNic(),dto.getSalaryPerHour(),dto.getContact(),dto.getUserID()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }
}
