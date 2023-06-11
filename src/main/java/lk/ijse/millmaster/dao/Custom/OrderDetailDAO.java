package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.SuperDAO;
import lk.ijse.millmaster.dto.CartDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends SuperDAO {
    boolean save(String oid, List<CartDTO> cartDTOList) throws SQLException;
}
