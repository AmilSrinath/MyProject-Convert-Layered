package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.SupplierBO;
import lk.ijse.millmaster.dao.Custom.SupplierDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.SupplierDTO;
import lk.ijse.millmaster.dto.UserDTO;
import lk.ijse.millmaster.entity.Supplier;
import lk.ijse.millmaster.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> allSupplier= new ArrayList<>();
        List<Supplier> all = supplierDAO.getAll();
        for (Supplier s : all) {
            allSupplier.add(new SupplierDTO(s.getId(),s.getName(),s.getContact(),s.getNic(),s.getAddress()));
        }
        return allSupplier;
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(dto.getId(),dto.getName(),dto.getContact(),dto.getNic(),dto.getAddress()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getId(),dto.getName(),dto.getContact(),dto.getNic(),dto.getAddress()));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }
}
