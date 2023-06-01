package lk.ijse.millmaster.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserTM {
    private String id;
    private String name;
    private String password;
    private String nic;
    private String email;
}
