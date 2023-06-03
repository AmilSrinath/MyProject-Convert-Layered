package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dto.UserDTO;

import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(UserDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(UserDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
