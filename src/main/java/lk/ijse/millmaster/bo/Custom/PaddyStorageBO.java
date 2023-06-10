package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.PaddyStorageDTO;

import java.sql.SQLException;
import java.util.List;

public interface PaddyStorageBO extends SuperBO {
    public List<PaddyStorageDTO> getAllPaddyStorages() throws SQLException, ClassNotFoundException;
    public boolean addPaddyStorage(PaddyStorageDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updatePaddyStorage(PaddyStorageDTO dto) throws SQLException, ClassNotFoundException;
    public boolean deletePaddyStorage(String id) throws SQLException, ClassNotFoundException;
    public String generateNewPaddyStorageID() throws SQLException, ClassNotFoundException;
}
