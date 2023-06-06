package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Product;

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
            Product product = new Product(rst.getString("Product_ID"),rst.getInt("Product_Quntity"),rst.getInt("Paddy_Quntity"),rst.getString("Product_Type"),rst.getString("Product_Manufact"),rst.getString("Product_Expire"),rst.getString("Stock_ID"));
            allProduct.add(product);
        }
        return allProduct;
    }

    @Override
    public boolean add(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO production(Product_ID , Product_Quntity, Paddy_Quntity, Product_Type, Product_Manufact, Product_Expire, Stock_ID) VALUES(?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getQuntity(),
                entity.getPaddyQun(),
                entity.getType(),
                entity.getManufact(),
                entity.getExpire(),
                entity.getSid());
    }

    @Override
    public boolean update(Product entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Production SET Product_Quntity = ?, Paddy_Quntity = ?, Product_Type = ?, Product_Manufact = ?, Product_Expire = ?, Stock_ID = ? WHERE Product_ID = ? ",
                entity.getQuntity(),
                entity.getPaddyQun(),
                entity.getType(),
                entity.getManufact(),
                entity.getExpire(),
                entity.getSid(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM production WHERE Product_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Product_ID FROM production ORDER BY Product_ID DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("P0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "P00"+id;
            }else {
                if (length < 3){
                    return "P0"+id;
                }else {
                    return "P"+id;
                }
            }
        } else {
            return "P001";
        }
    }

    @Override
    public List<String> loadStockID() throws SQLException {
        List<String> allStockIds = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT Stock_ID FROM stock");
        while (rst.next()) {
            String Ids = new String(rst.getString("Stock_ID"));
            allStockIds.add(Ids);
        }
        return allStockIds;
    }
}
