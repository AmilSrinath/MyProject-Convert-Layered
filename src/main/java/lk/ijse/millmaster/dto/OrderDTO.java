package lk.ijse.millmaster.dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDTO {
    private String id;
    private String date;
    private String buyerId;
    private String status;
}
