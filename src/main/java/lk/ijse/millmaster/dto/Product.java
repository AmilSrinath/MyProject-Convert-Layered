package lk.ijse.millmaster.dto;

import lombok.*;

import java.util.Date;

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
