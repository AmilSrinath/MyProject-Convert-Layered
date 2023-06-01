package lk.ijse.millmaster.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaddyStorageTM {
    private String id;
    private String padddyType;
    private double paddyQuntity;
    private int noOfBag;
    private double unitPrice;
    private String date;
    private String sector;
    private double total;
    private String supplierId;
    private String status;
}
