package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.SuperDAO;

import java.sql.SQLException;
import java.util.List;

public interface ForgotPasswordDAO extends SuperDAO {
    public String searchByName(String username) throws SQLException;
    public List<String> loadUserNames() throws SQLException;

    public boolean updatePassword(String userName, String password) throws SQLException;
}
