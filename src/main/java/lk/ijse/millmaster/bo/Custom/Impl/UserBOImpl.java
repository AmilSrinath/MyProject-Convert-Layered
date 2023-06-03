package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.UserBO;
import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserDTO> allUsers= new ArrayList<>();
        ArrayList<User> all = userDAO.getAll();
        for (User c : all) {
            allUsers.add(new UserDTO(c.getId(),c.getName(),c.getPassword(),c.getNic(),c.getEmail()));
        }
        return allUsers;
    }

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getId(),dto.getName(),dto.getPassword(),dto.getNic(),dto.getEmail()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getId(),dto.getName(),dto.getPassword(),dto.getNic(),dto.getEmail()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException {
        return userDAO.generateNewID();
    }
}
