package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.User;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public boolean addUser(User dto) throws SQLException, ClassNotFoundException ;

    public boolean updateUser(User dto) throws SQLException, ClassNotFoundException ;

    public boolean existUser(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException;
}
