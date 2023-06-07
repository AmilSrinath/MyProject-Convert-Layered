package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.BuyerBO;
import lk.ijse.millmaster.dao.Custom.BuyerDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.BuyerDTO;
import lk.ijse.millmaster.entity.Buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerBOImpl implements BuyerBO {
    BuyerDAO buyerDAO = (BuyerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BUYER);

    @Override
    public List<BuyerDTO> getAllBuyer() throws SQLException, ClassNotFoundException {
        List<BuyerDTO> allUsers= new ArrayList<>();
        List<Buyer> all = buyerDAO.getAll();
        for (Buyer b : all) {
            allUsers.add(new BuyerDTO(b.getId(),b.getName(),b.getContact(),b.getShopName(),b.getAddress(),b.getUserID() ));
        }
        return allUsers;
    }

    @Override
    public boolean addBuyer(BuyerDTO dto) throws SQLException, ClassNotFoundException {
        return buyerDAO.add(new Buyer(dto.getId(), dto.getName(), dto.getContact(), dto.getShopName(), dto.getAddress(), dto.getUserID()));
    }

    @Override
    public boolean updateBuyer(BuyerDTO dto) throws SQLException, ClassNotFoundException {
        return buyerDAO.update(new Buyer(dto.getId(),dto.getName(),dto.getContact(),dto.getShopName(),dto.getAddress(),dto.getUserID()));
    }

    @Override
    public boolean deleteBuyer(String id) throws SQLException, ClassNotFoundException {
        return buyerDAO.delete(id);
    }

    @Override
    public String generateNewBuyerID() throws SQLException, ClassNotFoundException {
        return buyerDAO.generateNewID();
    }
}
