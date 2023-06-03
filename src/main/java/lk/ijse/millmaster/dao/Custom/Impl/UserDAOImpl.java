package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.entity.User;

import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user(User_ID , User_Name, User_Password,User_NIC, User_Email) VALUES(?,?,?,?,?)",entity.getId(),entity.getName(),entity.getPassword(),entity.getNic(),entity.getEmail());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
