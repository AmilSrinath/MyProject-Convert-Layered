package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.OrderBO;
import lk.ijse.millmaster.dao.Custom.OrderDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.OrderDTO;
import lk.ijse.millmaster.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public List<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        List<OrderDTO> arrayList= new ArrayList<>();
        List<Order> orders = orderDAO.getAll();
        for (Order o : orders) {
            arrayList.add(new OrderDTO(o.getId(),o.getDate(),o.getBuyerId(),o.getStatus()));
        }
        return arrayList;
    }

    @Override
    public boolean addOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.add(new Order(dto.getId(),dto.getDate(),dto.getBuyerId(),dto.getStatus()));
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(dto.getId(),dto.getDate(),dto.getBuyerId(),dto.getStatus()));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }
}
