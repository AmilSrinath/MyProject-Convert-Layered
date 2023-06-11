package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.NotificationDAO;
import lk.ijse.millmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDAOImpl implements NotificationDAO {
    @Override
    public int OrderActive() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT Count(Status)FROM orders WHERE Status='Active'");
        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }
}
