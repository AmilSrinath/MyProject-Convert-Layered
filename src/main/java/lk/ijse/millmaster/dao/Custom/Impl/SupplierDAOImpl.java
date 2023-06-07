package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.SupplierDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Supplier;
import lk.ijse.millmaster.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        List<Supplier> allSupliers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("Supplier_ID"), rst.getString("Supplier_Name"),rst.getString("Supplier_Contact"),rst.getString("Supplier_NIC"),rst.getString("Supplier_Address"));
            allSupliers.add(supplier);
        }
        return allSupliers;
    }

    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier(supplier_ID , supplier_Name, supplier_Contact,supplier_nic,supplier_Address) VALUES(?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getContact(),
                entity.getNic(),
                entity.getAddress());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Supplier SET Supplier_Name = ?, Supplier_Contact = ?, Supplier_NIC=?, Supplier_Address=? WHERE Supplier_ID = ?",
                entity.getName(),
                entity.getContact(),
                entity.getNic(),
                entity.getAddress(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Supplier WHERE Supplier_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Supplier_ID FROM supplier ORDER BY Supplier_ID DESC LIMIT 1;");
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
}
