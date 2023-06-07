package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.BuyerDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Buyer;
import lk.ijse.millmaster.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAOImpl implements BuyerDAO {
    @Override
    public List<Buyer> getAll() throws SQLException, ClassNotFoundException {
        List<Buyer> allBuyers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM buyer");
        while (rst.next()) {
            Buyer buyer = new Buyer(rst.getString("Buyer_ID"), rst.getString("Buyer_Name"),rst.getString("Buyer_Cont_Num"),rst.getString("Buyer_Shop"),rst.getString("Buyer_Address"),rst.getString("User_ID"));
            allBuyers.add(buyer);
        }
        return allBuyers;
    }

    @Override
    public boolean add(Buyer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO buyer(Buyer_ID , Buyer_Name, Buyer_Cont_Num,Buyer_Shop,Buyer_Address,User_ID) VALUES(?,?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getContact(),
                entity.getShopName(),
                entity.getAddress(),
                entity.getUserID());
    }

    @Override
    public boolean update(Buyer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Buyer SET Buyer_Name = ?, Buyer_Cont_Num = ?, Buyer_Shop=?, Buyer_Address=?, User_ID=? WHERE Buyer_ID = ?",entity.getName(),entity.getContact(),entity.getShopName(),entity.getAddress(),entity.getUserID(),entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM buyer WHERE Buyer_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Buyer_ID FROM buyer ORDER BY Buyer_ID DESC LIMIT 1");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("B0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "B00"+id;
            }else {
                if (length < 3){
                    return "B0"+id;
                }else {
                    return "B"+id;
                }
            }
        } else {
            return "B001";
        }
    }

    @Override
    public String searchByName(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM User WHERE User_Name = ?",id);
        if(rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
