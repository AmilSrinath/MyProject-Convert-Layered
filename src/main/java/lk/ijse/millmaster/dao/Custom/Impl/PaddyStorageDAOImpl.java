package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.PaddyStorageDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.PaddyStorage;
import lk.ijse.millmaster.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaddyStorageDAOImpl implements PaddyStorageDAO {
    @Override
    public List<PaddyStorage> getAll() throws SQLException, ClassNotFoundException {
        List<PaddyStorage> allPaddyStorage = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM stock");
        while (rst.next()) {
            PaddyStorage paddyStorage = new PaddyStorage(rst.getString("Stock_ID"),rst.getString("Paddy_Type"),rst.getDouble("Paddy_Quntity"),rst.getInt("No_Of_Bag"),rst.getDouble("Unit_Price"),rst.getString("Date"),rst.getString("Sector"),rst.getDouble("Total"),rst.getString("Supplier_ID"),rst.getString("Status"));
            allPaddyStorage.add(paddyStorage);
        }
        return allPaddyStorage;
    }

    @Override
    public boolean add(PaddyStorage entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO stock(stock_ID , Paddy_Type, Paddy_Quntity, No_Of_Bag, Unit_Price, Date, Sector, Total, Supplier_ID,Status) VALUES(?,?,?,?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getPaddyType(),
                entity.getPaddyQuntity(),
                entity.getNoOfBag(),
                entity.getUnitPrice(),
                entity.getDate(),
                entity.getSector(),
                entity.getTotal(),
                entity.getSupplierId(),
                entity.getStatus());
    }

    @Override
    public boolean update(PaddyStorage entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE stock SET Paddy_Type = ?, Paddy_Quntity = ?, No_Of_Bag = ?, Unit_Price = ?, Date = ?, Sector = ?,Total = ? ,Supplier_ID = ? WHERE Stock_ID = ?",
                entity.getPaddyType(),
                entity.getPaddyQuntity(),
                entity.getNoOfBag(),
                entity.getUnitPrice(),
                entity.getDate(),
                entity.getSector(),
                entity.getTotal(),
                entity.getSupplierId(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM stock WHERE stock_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Stock_ID FROM stock ORDER BY Stock_ID DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "S00"+id;
            }else {
                if (length < 3){
                    return "S0"+id;
                }else {
                    return "S"+id;
                }
            }
        } else {
            return "S001";
        }
    }

    @Override
    public List<String> loadUserNames() throws SQLException {
        List<String> allUserNames = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT Supplier_ID FROM supplier");
        while (rst.next()) {
            String Ids = new String(rst.getString("Supplier_ID"));
            allUserNames.add(Ids);
        }
        return allUserNames;
    }
}
