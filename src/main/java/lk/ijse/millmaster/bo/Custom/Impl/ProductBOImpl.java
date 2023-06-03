package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.ProductBO;
import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.entity.Product;
import lk.ijse.millmaster.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    @Override
    public List<ProductDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<ProductDTO> allProduct= new ArrayList<>();
        List<Product> productList = productDAO.getAll();
        for (Product p : productList) {
            allProduct.add(new ProductDTO(p.getId(),p.getQuntity(),p.getType(),p.getManufact(),p.getExpire(),p.getSid()));
        }
        return allProduct;
    }

    @Override
    public boolean addUser(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.add(new Product(dto.getId(),dto.getQuntity(),dto.getType(),dto.getManufact(),dto.getExpire(),dto.getSid()));
    }

    @Override
    public boolean updateUser(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
