package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.AssestDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Assest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssestDAOImpl implements AssestDAO {
    @Override
    public List<Assest> getAll() throws SQLException, ClassNotFoundException {
        List<Assest> assestList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM assest");
        while (rst.next()) {
            Assest assest = new Assest(rst.getString("Assest_ID"),rst.getString("Assest_Name"),rst.getInt("Assest_Quntity"),rst.getString("User_ID"));
            assestList.add(assest);
        }
        return assestList;
    }

    @Override
    public boolean add(Assest entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO assest(assest_ID , assest_Name, assest_quntity,User_ID) VALUES(?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getQun(),
                entity.getUserID());
    }

    @Override
    public boolean update(Assest entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Assest SET Assest_name = ?, Assest_quntity = ?,User_ID = ? WHERE Assest_ID = ?",
                entity.getName(),
                entity.getQun(),
                entity.getUserID(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM assest WHERE Assest_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT Assest_ID FROM assest ORDER BY Assest_ID DESC LIMIT 1;");
        if (rst.next()) {
            String string=rst.getString(1);
            String[] strings = string.split("A0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "A00"+id;
            }else {
                if (length < 3){
                    return "A0"+id;
                }else {
                    return "A"+id;
                }
            }
        } else {
            return "A001";
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
