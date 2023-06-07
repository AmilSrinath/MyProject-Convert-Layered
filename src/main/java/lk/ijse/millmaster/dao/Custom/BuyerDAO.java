package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.entity.Buyer;

import java.sql.SQLException;

public interface BuyerDAO extends CrudDAO<Buyer> {
    public String searchByName(String text) throws SQLException;
}
