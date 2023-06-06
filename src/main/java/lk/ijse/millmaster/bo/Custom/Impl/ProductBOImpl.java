package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.ProductBO;
import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    @Override
    public List<ProductDTO> getAllProduct() throws SQLException, ClassNotFoundException {
        List<ProductDTO> allProduct= new ArrayList<>();
        List<Product> productList = productDAO.getAll();
        for (Product p : productList) {
            allProduct.add(new ProductDTO(p.getId(),p.getQuntity(), p.getPaddyQun(), p.getType(),p.getManufact(),p.getExpire(),p.getSid()));
        }
        return allProduct;
    }

    @Override
    public boolean addProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.add(new Product(
                dto.getId(),
                dto.getQuntity(),
                dto.getPaddyQun(),
                dto.getType(),
                dto.getManufact(),
                dto.getExpire(),
                dto.getSid()));
    }

    @Override
    public boolean updateProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return productDAO.update(new Product(
                dto.getId(),
                dto.getQuntity(),
                dto.getPaddyQun(),
                dto.getType(),
                dto.getManufact(),
                dto.getExpire(),
                dto.getSid()));
    }

    @Override
    public boolean deleteProduct(String id) throws SQLException, ClassNotFoundException {
        return productDAO.delete(id);
    }

    @Override
    public String generateNewProductID() throws SQLException, ClassNotFoundException {
        return productDAO.generateNewID();
    }
}
