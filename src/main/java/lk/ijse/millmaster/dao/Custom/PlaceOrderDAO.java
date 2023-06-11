package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.SuperDAO;
import lk.ijse.millmaster.dto.CartDTO;
import lk.ijse.millmaster.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderDAO extends SuperDAO {
    public List<String> loadProductID() throws SQLException;
    public  ProductDTO searchById(String code) throws SQLException;
}
