package lk.ijse.millmaster.bo.Custom;

import lk.ijse.millmaster.bo.SuperBO;
import lk.ijse.millmaster.dto.AssestDTO;
import lk.ijse.millmaster.dto.AttendanceDTO;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceBO extends SuperBO {
    public List<AttendanceDTO> getAllAttendance() throws SQLException, ClassNotFoundException;
    public boolean addAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException;

    public String generateNewAttendanceID() throws SQLException, ClassNotFoundException;
}
