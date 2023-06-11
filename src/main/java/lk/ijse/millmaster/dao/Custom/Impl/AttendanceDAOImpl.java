package lk.ijse.millmaster.dao.Custom.Impl;

import lk.ijse.millmaster.dao.Custom.AttendanceDAO;
import lk.ijse.millmaster.dao.SQLUtil;
import lk.ijse.millmaster.entity.Attendance;
import lk.ijse.millmaster.entity.Buyer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public List<Attendance> getAll() throws SQLException, ClassNotFoundException {
        List<Attendance> attendances = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM attendance");
        while (rst.next()) {
            Attendance attendance = new Attendance(rst.getString("Attendance_ID"),rst.getInt("Working_Hour"),rst.getString("Emp_ID"),rst.getDouble("Salary"));
            attendances.add(attendance);
        }
        return attendances;
    }

    @Override
    public boolean add(Attendance entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO attendance(Attendance_ID,Working_Hour,Emp_ID,Salary) VALUES(?,?,?,?)",
                entity.getId(),
                entity.getHour(),
                entity.getEmp_id(),
                entity.getSalary());
    }

    @Override
    public boolean update(Attendance entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE attendance SET Working_Hour = ?, Salary=? WHERE Attendance_ID = ?",
                entity.getHour(),
                entity.getSalary(),
                entity.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM attendance WHERE Attendance_ID = ?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Attendance_ID FROM attendance ORDER BY Attendance_ID DESC LIMIT 1");
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
}
