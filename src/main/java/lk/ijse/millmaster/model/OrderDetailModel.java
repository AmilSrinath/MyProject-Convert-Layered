package lk.ijse.millmaster.model;

import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.CartDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String oid, List<CartDTO> cartDTOList) throws SQLException {
        for(CartDTO dto :  cartDTOList) {
            if(!save(oid, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, CartDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO order_product(Product_ID, Order_ID, Quntity, Unit_Price, Total) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, dto.getCode());
        pstm.setString(2, oId);
        pstm.setInt(3, dto.getQty());
        pstm.setDouble(4, dto.getUnitPrice());
        pstm.setDouble(5, dto.getUnitPrice() * dto.getQty());

        return pstm.executeUpdate() > 0;

    }
}
