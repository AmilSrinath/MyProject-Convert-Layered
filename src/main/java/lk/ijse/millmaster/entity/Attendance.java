package lk.ijse.millmaster.entity;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Attendance {
    private String id;
    private int hour;
    private String emp_id;
    private Double salary;
}
