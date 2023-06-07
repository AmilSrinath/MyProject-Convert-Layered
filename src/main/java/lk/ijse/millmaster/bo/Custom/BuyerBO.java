package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.BuyerDTO;

import java.sql.SQLException;
import java.util.List;

public interface BuyerBO extends SuperBO {
    public List<BuyerDTO> getAllBuyer() throws SQLException, ClassNotFoundException;
    public boolean addBuyer(BuyerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateBuyer(BuyerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteBuyer(String id) throws SQLException, ClassNotFoundException;

    public String generateNewBuyerID() throws SQLException, ClassNotFoundException;
}
