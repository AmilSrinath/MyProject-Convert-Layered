package lk.ijse.millmaster.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BuyerDTO {
    private String id;
    private String name;
    private String contact;
    private String shopName;
    private String address;
    private String userID;
}
