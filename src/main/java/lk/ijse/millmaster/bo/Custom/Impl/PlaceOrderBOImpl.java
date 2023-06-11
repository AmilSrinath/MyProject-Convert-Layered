package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.PlaceOrderBO;
import lk.ijse.millmaster.dao.Custom.OrderDAO;
import lk.ijse.millmaster.dao.Custom.OrderDetailDAO;
import lk.ijse.millmaster.dao.Custom.ProductDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.db.DBConnection;
import lk.ijse.millmaster.dto.CartDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean placeOrder(String oid, List<CartDTO> cartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isSaved = orderDAO.updateOrderStatus(oid);
            if (isSaved) {
                boolean isUpdated = productDAO.updateQty(cartDTOList);
                if (isUpdated) {
                    boolean isOrderDetailSaved = orderDetailDAO.save(oid, cartDTOList);
                    if (isOrderDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }
}
