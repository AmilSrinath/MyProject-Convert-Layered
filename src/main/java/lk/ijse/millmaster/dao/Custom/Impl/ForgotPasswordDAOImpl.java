package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.ForgotPasswordDAO;
import lk.ijse.millmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {
    @Override
    public String searchByName(String username) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT User_Email FROM User WHERE User_Name = ?",username);
        if(rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<String> loadUserNames() throws SQLException {
        List<String> allUserName = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT User_Name FROM user");
        while (rst.next()) {
            String Ids = new String(rst.getString("User_Name"));
            allUserName.add(Ids);
        }
        return allUserName;
    }

    @Override
    public boolean updatePassword(String userName, String password) throws SQLException {
        return SQLUtil.execute("UPDATE User SET User_password = ? WHERE User_Name = ?",password,userName);
    }
}
