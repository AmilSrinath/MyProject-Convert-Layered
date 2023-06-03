package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Product;
import lk.ijse.millmaster.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> getAll() throws SQLException, ClassNotFoundException {
        List<Product> allProduct = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM production");
        while (rst.next()) {
            Product product = new Product(rst.getString("Product_ID"),rst.getInt("Product_Quntity"),rst.getString("Product_Type"),rst.getString("Product_Manufact"),rst.getString("Product_Expire"),rst.getString("Stock_ID"));
            allProduct.add(product);
        }
        return allProduct;
    }

    @Override
    public boolean add(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO production(Product_ID , Product_Quntity, Product_Type, Product_Manufact, Product_Expire, Stock_ID) VALUES(?,?,?,?,?,?)",entity.getId(),entity.getQuntity(),entity.getType(),entity.getManufact(),entity.getExpire(),entity.getSid());
    }

    @Override
    public boolean update(Product entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
