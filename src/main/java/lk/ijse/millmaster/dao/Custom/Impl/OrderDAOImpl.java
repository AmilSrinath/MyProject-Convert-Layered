package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.OrderDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Order;
import lk.ijse.millmaster.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        List<Order> allOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders");
        while (rst.next()) {
            Order order = new Order(rst.getString("Order_ID"),rst.getString("Order_Date"),rst.getString("Buyer_ID"),rst.getString("Status"));
            allOrders.add(order);
        }
        return allOrders;
    }

    @Override
    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders(order_ID , order_Date, Buyer_ID, Status) VALUES(?,?,?,?)",
                entity.getId(),
                entity.getDate(),
                entity.getBuyerId(),
                entity.getStatus());
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE orders SET Order_Date = ?, Buyer_ID = ?, Status = ? WHERE Order_ID = ?",
                entity.getDate(),
                entity.getBuyerId(),
                entity.getStatus(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orders WHERE Order_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Order_ID FROM orders ORDER BY Order_ID DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "O00"+id;
            }else {
                if (length < 3){
                    return "O0"+id;
                }else {
                    return "O"+id;
                }
            }
        } else {
            return "O001";
        }
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
    public List<String> loadUserNames() throws SQLException {
        List<String> allBuyerID = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT Buyer_ID FROM buyer");
        while (rst.next()) {
            String Ids = new String(rst.getString("Buyer_ID"));
            allBuyerID.add(Ids);
        }
        return allBuyerID;
    }

    @Override
    public boolean updateOrderStatus(String oid) throws SQLException {
        return SQLUtil.execute("UPDATE orders SET Status=? WHERE Order_ID=?","Complete",oid);
    }
}
