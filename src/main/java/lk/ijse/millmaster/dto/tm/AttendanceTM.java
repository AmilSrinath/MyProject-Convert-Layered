package lk.ijse.millmaster.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceTM {
    private String id;
    private int hour;
    private String emp_id;
    private Double salary;
}
