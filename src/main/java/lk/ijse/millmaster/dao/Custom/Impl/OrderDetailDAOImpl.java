package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.OrderDetailDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.dto.CartDTO;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean save(String oid, List<CartDTO> cartDTOList) throws SQLException {
        for (CartDTO dto : cartDTOList) {
            boolean isSaved = SQLUtil.execute("INSERT INTO order_product(Product_ID, Order_ID, Quntity, Unit_Price, Total) VALUES (?, ?, ?, ?, ?)",
                    dto.getCode(),
                    oid,
                    dto.getQty(),
                    dto.getUnitPrice(),
                    dto.getUnitPrice() * dto.getQty());
            if (!isSaved){
                return false;
            }
        }
        return true;
    }
}
