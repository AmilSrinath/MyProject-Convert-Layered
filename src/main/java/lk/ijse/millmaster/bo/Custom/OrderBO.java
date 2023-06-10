package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    public List<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException;
    public boolean addOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    public String generateNewOrderID() throws SQLException, ClassNotFoundException;
}
