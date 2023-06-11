package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.SuperDAO;

import java.sql.SQLException;

public interface HomeDAO extends SuperDAO {
    public int OrderActive() throws SQLException;
}
