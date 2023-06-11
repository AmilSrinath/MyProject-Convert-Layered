package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.DashboardDAO;
import lk.ijse.millmaster.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl implements DashboardDAO {
    @Override
    public int OrderComplete() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT Count(Status)FROM orders WHERE Status='Complete'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

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

    @Override
    public int getRedRawSum5kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Raw Rice 5Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getRedRawSum10kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Raw Rice 10Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getRedRawSum25kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Raw Rice 25Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getKeerisambaSum5kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Keerisamba 5Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getKeerisambaSum10kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Keerisamba 10Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getKeerisambaSum25kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Keerisamba 25Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getWhiteRawSum5kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='White Raw Rice 5Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getWhiteRawSum10kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='White Raw Rice 10Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getWhiteRawSum25kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='White Raw Rice 25Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getSambaSum5kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Samba 5Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getSambaSum10kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Samba 10Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getSambaSum25kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Samba 25Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getNaduSum5kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Nadu 5Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getNaduSum10kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Nadu 10Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getNaduSum25kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Nadu 25Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getRedNaduSum5kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Nadu 5Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getRedNaduSum10kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Nadu 10Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getRedNaduSum25kg() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production WHERE Product_Type='Red Nadu 25Kg'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddyQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddySambaQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Samba'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddyNaduQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Nadu'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddyRedNaduQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Red Nadu'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddyWhiteRawQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='White Raw Rice'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddyKeerisambaQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Keerisamba'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getPaddyRedRowQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Paddy_Quntity) FROM stock WHERE Paddy_Type='Red Raw Rice'");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }

    @Override
    public int getProductQuntityColSum() throws SQLException {
        int sum=0;
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(Product_Quntity) FROM production");

        while (resultSet.next()){
            int c = resultSet.getInt(1);
            sum=sum+c;
        }
        return sum;
    }
}
