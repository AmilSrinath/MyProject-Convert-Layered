package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        List<User> allUsers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM user");
        while (rst.next()) {
            User user = new User(rst.getString("user_id"), rst.getString("User_Name"),rst.getString("User_Password"),rst.getString("User_NIC"),rst.getString("User_Email"));
            allUsers.add(user);
        }
        return allUsers;
    }

    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user(User_ID , User_Name, User_Password,User_NIC, User_Email) VALUES(?,?,?,?,?)",entity.getId(),entity.getName(),entity.getPassword(),entity.getNic(),entity.getEmail());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE User SET User_name = ?, User_password = ?, User_nic = ?, User_email = ?  WHERE User_id = ?",entity.getName(),entity.getPassword(),entity.getNic(),entity.getEmail(),entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM user WHERE User_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT User_ID FROM User ORDER BY User_ID DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
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
        } else {
            return "U001";
        }
    }
}
