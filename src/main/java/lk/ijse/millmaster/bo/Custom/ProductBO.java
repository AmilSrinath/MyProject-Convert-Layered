package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductBO extends SuperBO {
    public List<ProductDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    public boolean addUser(ProductDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateUser(ProductDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    public String generateNewUserID() throws SQLException, ClassNotFoundException;
}
