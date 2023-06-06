package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.AssestBO;
import lk.ijse.millmaster.dao.Custom.AssestDAO;
import lk.ijse.millmaster.dao.Custom.UserDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.ProductDTO;
import lk.ijse.millmaster.entity.Assest;
import lk.ijse.millmaster.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssestBOImpl implements AssestBO {

    AssestDAO assestDAO = (AssestDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ASSEST);

    @Override
    public List<AssestDTO> getAllAssest() throws SQLException, ClassNotFoundException {
        List<AssestDTO> arrayList= new ArrayList<>();
        List<Assest> assestlist = assestDAO.getAll();
        for (Assest a : assestlist) {
            arrayList.add(new AssestDTO(a.getId(),a.getName(),a.getQun(),a.getUserID()));
        }
        return arrayList;
    }

    @Override
    public boolean addAssest(AssestDTO dto) throws SQLException, ClassNotFoundException {
        return assestDAO.add(new Assest(dto.getId(),dto.getName(),dto.getQun(),dto.getUserID()));
    }

    @Override
    public boolean updateAssest(AssestDTO dto) throws SQLException, ClassNotFoundException {
        return assestDAO.update(new Assest(dto.getId(),dto.getName(),dto.getQun(),dto.getUserID()));
    }

    @Override
    public boolean deleteAssest(String id) throws SQLException, ClassNotFoundException {
        return assestDAO.delete(id);
    }

    @Override
    public String generateNewAssestID() throws SQLException, ClassNotFoundException {
        return assestDAO.generateNewID();
    }
}
