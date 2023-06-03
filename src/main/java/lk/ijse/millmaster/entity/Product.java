package lk.ijse.millmaster.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String id;
    private int quntity;
    private String type;
    private String manufact;
    private String expire;
    private String sid;
}
