package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.entity.Assest;

import java.sql.SQLException;

public interface AssestDAO extends CrudDAO<Assest> {
    public String searchByName(String text) throws SQLException;
}
