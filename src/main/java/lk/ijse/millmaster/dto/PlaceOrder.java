package lk.ijse.millmaster.dto;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrder {
    private String pid;
    private String oid;
    private int quntity;
    private Double unitPrice;
    private Double total;
    private Button btn;
}
