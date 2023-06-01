package lk.ijse.millmaster.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Supplier {
    private String id;
    private String name;
    private String contact;
    private String nic;
    private String address;
}
