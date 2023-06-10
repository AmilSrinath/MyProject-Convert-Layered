package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.PaddyStorageBO;
import lk.ijse.millmaster.dao.Custom.OrderDAO;
import lk.ijse.millmaster.dao.Custom.PaddyStorageDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.OrderDTO;
import lk.ijse.millmaster.dto.PaddyStorageDTO;
import lk.ijse.millmaster.entity.Order;
import lk.ijse.millmaster.entity.PaddyStorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaddyStorageBOImpl implements PaddyStorageBO {

    PaddyStorageDAO paddyStorageDAO = (PaddyStorageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PADDYSTORAGE);
    @Override
    public List<PaddyStorageDTO> getAllPaddyStorages() throws SQLException, ClassNotFoundException {
        List<PaddyStorageDTO> arrayList= new ArrayList<>();
        List<PaddyStorage> paddyStorages = paddyStorageDAO.getAll();
        for (PaddyStorage p : paddyStorages) {
            arrayList.add(new PaddyStorageDTO(p.getId(),p.getPaddyType(),p.getPaddyQuntity(),p.getNoOfBag(),p.getUnitPrice(),p.getDate(),p.getSector(),p.getTotal(),p.getSupplierId(),p.getStatus()));
        }
        return arrayList;
    }

    @Override
    public boolean addPaddyStorage(PaddyStorageDTO dto) throws SQLException, ClassNotFoundException {
        return paddyStorageDAO.add(new PaddyStorage(dto.getId(),dto.getPaddyType(),dto.getPaddyQuntity(),dto.getNoOfBag(),dto.getUnitPrice(),dto.getDate(),dto.getSector(),dto.getTotal(),dto.getSupplierId(),dto.getStatus()));
    }

    @Override
    public boolean updatePaddyStorage(PaddyStorageDTO dto) throws SQLException, ClassNotFoundException {
        return paddyStorageDAO.update(new PaddyStorage(dto.getId(),dto.getPaddyType(),dto.getPaddyQuntity(),dto.getNoOfBag(),dto.getUnitPrice(),dto.getDate(),dto.getSector(),dto.getTotal(),dto.getSupplierId(),dto.getStatus()));
    }

    @Override
    public boolean deletePaddyStorage(String id) throws SQLException, ClassNotFoundException {
        return paddyStorageDAO.delete(id);
    }

    @Override
    public String generateNewPaddyStorageID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
