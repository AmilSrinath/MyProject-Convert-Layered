package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.UserBO;
import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean addUser(User dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getId(),dto.getName(),dto.getPassword(),dto.getNic(),dto.getEmail()));
    }

    @Override
    public boolean updateUser(User dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existUser(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
