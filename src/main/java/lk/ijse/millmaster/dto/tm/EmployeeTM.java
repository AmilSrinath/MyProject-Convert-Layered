package lk.ijse.millmaster.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTM {
    private String id;
    private String name;
    private String address;
    private String nic;
    private Double salaryPerHour;
    private String contact;
    private String userID;
}
