package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.User;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public boolean addCustomer(User dto) throws SQLException, ClassNotFoundException ;

    public boolean updateCustomer(User dto) throws SQLException, ClassNotFoundException ;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
}
