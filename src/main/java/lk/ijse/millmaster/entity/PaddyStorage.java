package lk.ijse.millmaster.entity;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaddyStorage {
    private String id;
    private String paddyType;
    private double paddyQuntity;
    private int noOfBag;
    private double unitPrice;
    private String date;
    private String sector;
    private double total;
    private String supplierId;
    private String status;
}
