package lk.ijse.millmaster.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
    private String id;
    private int quntity;
    private int paddyQun;
    private String type;
    private String manufact;
    private String expire;
    private String sid;
}
