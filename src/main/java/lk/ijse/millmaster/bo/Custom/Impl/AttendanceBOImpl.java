package lk.ijse.millmaster.bo.Custom.Impl;

import lk.ijse.millmaster.bo.Custom.AttendanceBO;
import lk.ijse.millmaster.dao.Custom.AttendanceDAO;
import lk.ijse.millmaster.dao.Custom.BuyerDAO;
import lk.ijse.millmaster.dao.DAOFactory;
import lk.ijse.millmaster.dto.AttendanceDTO;
import lk.ijse.millmaster.dto.BuyerDTO;
import lk.ijse.millmaster.entity.Attendance;
import lk.ijse.millmaster.entity.Buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    @Override
    public List<AttendanceDTO> getAllAttendance() throws SQLException, ClassNotFoundException {
        List<AttendanceDTO> allAttendances= new ArrayList<>();
        List<Attendance> all = attendanceDAO.getAll();
        for (Attendance a : all) {
            allAttendances.add(new AttendanceDTO(a.getId(),a.getHour(),a.getEmp_id(),a.getSalary()));
        }
        return allAttendances;
    }

    @Override
    public boolean addAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException {
        return attendanceDAO.add(new Attendance(dto.getId(),dto.getHour(),dto.getEmp_id(),dto.getSalary()));
    }

    @Override
    public boolean updateAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException {
        return attendanceDAO.update(new Attendance(dto.getId(),dto.getHour(),dto.getEmp_id(),dto.getSalary()));
    }

    @Override
    public boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException {
        return attendanceDAO.delete(id);
    }

    @Override
    public String generateNewAttendanceID() throws SQLException, ClassNotFoundException {
        return attendanceDAO.generateNewID();
    }
}
