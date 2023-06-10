package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.entity.PaddyStorage;

import java.sql.SQLException;
import java.util.List;

public interface PaddyStorageDAO extends CrudDAO<PaddyStorage> {
    public List<String> loadUserNames() throws SQLException;
}
