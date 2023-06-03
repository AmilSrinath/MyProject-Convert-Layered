package lk.ijse.millmaster.model;

import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public static List<UserDTO> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";

        List<UserDTO> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new UserDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static List<String> getUserName() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT User_Name FROM user";

        List<String> username = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            username.add(resultSet.getString(1));
        }
        return username;
    }

    public static UserDTO searchByUser_Name(String user_name) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE User_Name = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, user_name);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new UserDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static UserDTO searchByName(String name) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM User WHERE User_Name = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new UserDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT User_ID FROM user ORDER BY User_ID DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String string) {
        if(string != null) {
            String[] strings = string.split("U0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "U00"+id;
            }else {
                if (length < 3){
                    return "U0"+id;
                }else {
                    return "U"+id;
                }
            }
        }
        return "U001";
    }
}
