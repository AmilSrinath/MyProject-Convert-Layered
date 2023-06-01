package lk.ijse.millmaster.dao;

import java.sql.SQLException;

public interface CrudDAO<T> {
    public boolean add(T entity) throws SQLException, ClassNotFoundException;
    public boolean update(T entity) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
}
