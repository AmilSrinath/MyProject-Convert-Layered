package lk.ijse.millmaster.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceDTO {
    private String id;
    private int hour;
    private String emp_id;
    private Double salary;
}
