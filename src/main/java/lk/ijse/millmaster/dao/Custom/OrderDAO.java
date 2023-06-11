package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {
    public int OrderActive() throws SQLException;
    public List<String> loadUserNames() throws SQLException;
    boolean updateOrderStatus(String oid) throws SQLException;
}
