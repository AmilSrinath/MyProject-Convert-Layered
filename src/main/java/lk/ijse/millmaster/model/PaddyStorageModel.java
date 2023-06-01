package lk.ijse.millmaster.model;

import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.PaddyStorage;
import lk.ijse.millmaster.dto.Product;
import lk.ijse.millmaster.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaddyStorageModel {

    public static List<PaddyStorage> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Stock";

        List<PaddyStorage> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new PaddyStorage(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            ));
        }
        return data;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Stock_ID FROM stock ORDER BY Stock_ID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String string) {
        if(string != null) {
            String[] strings = string.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "S00"+id;
            }else {
                if (length < 3){
                    return "S0"+id;
                }else {
                    return "S"+id;
                }
            }
        }
        return "S001";
    }

    public static List<String> getStockID() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT Stock_ID FROM stock";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static PaddyStorage searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM stock WHERE Stock_ID = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new PaddyStorage(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            );
        }
        return null;
    }

    public static boolean updateQty(String sid, String quntity) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE stock SET Paddy_Quntity = (Paddy_Quntity - ?) WHERE Stock_ID = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, Integer.parseInt(quntity));
        pstm.setString(2, sid);

        return pstm.executeUpdate() > 0;
    }
}
