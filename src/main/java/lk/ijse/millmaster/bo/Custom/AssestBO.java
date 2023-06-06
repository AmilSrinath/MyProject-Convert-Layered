package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface AssestBO extends SuperBO {
    public List<AssestDTO> getAllAssest() throws SQLException, ClassNotFoundException;
    public boolean addAssest(AssestDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateAssest(AssestDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteAssest(String id) throws SQLException, ClassNotFoundException;

    public String generateNewAssestID() throws SQLException, ClassNotFoundException;
}
