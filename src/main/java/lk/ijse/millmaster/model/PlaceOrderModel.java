package lk.ijse.millmaster.model;

import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.CartDTO;
import lk.ijse.millmaster.dto.PlaceOrder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderModel {
    public static List<String> getProductID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT Product_ID FROM production";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static List<String> getOrderID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT Order_ID FROM orders";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static boolean placeOrder(String oid, List<CartDTO> cartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = OrderModel.save(oid);
            if (isSaved) {
                boolean isUpdated = ProductModel.updateQty(cartDTOList);
                if (isUpdated) {
                    boolean isOrderDetailSaved = OrderDetailModel.save(oid, cartDTOList);
                    if (isOrderDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }
}
