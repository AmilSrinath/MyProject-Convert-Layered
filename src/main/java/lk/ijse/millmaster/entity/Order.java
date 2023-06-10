package lk.ijse.millmaster.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Order {
    private String id;
    private String date;
    private String buyerId;
    private String status;
}
