package lk.ijse.millmaster.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderTM {
    private String id;
    private String date;
    private String buyerId;
    private String status;

}
