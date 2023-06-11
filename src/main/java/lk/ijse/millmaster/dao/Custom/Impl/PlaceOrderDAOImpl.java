package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.PlaceOrderDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.dto.CartDTO;
import lk.ijse.millmaster.dto.ProductDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {
    @Override
    public List<String> loadProductID() throws SQLException {
        List<String> allProductIDs = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT Product_ID FROM production");
        while (rst.next()) {
            String Ids = new String(rst.getString("Product_ID"));
            allProductIDs.add(Ids);
        }
        return allProductIDs;
    }

    @Override
    public ProductDTO searchById(String code) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM production WHERE Product_ID = ?",code);
        if(resultSet.next()) {
            return new ProductDTO(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }
}
