package lk.ijse.millmaster.dao.Custom;

import lk.ijse.millmaster.dao.CrudDAO;
import lk.ijse.millmaster.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO extends CrudDAO<Product> {
    public List<String> loadStockID() throws SQLException;
}
