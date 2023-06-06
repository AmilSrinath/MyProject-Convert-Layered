package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductBO extends SuperBO {
    public List<ProductDTO> getAllProduct() throws SQLException, ClassNotFoundException;
    public boolean addProduct(ProductDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateProduct(ProductDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteProduct(String id) throws SQLException, ClassNotFoundException;

    public String generateNewProductID() throws SQLException, ClassNotFoundException;
}
