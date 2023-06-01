package lk.ijse.millmaster.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private String id;
    private String name;
    private String password;
    private String nic;
    private String email;
}
