package lk.ijse.millmaster.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductTM {
    private String id;
    private int quntity;
    private String type;
    private String manufact;
    private String expire;
    private String sid;
}
