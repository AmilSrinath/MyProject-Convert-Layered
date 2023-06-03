package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    public String generateNewUserID() throws SQLException, ClassNotFoundException;
}
