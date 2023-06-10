package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
    public int OrderActive() throws SQLException;
}
