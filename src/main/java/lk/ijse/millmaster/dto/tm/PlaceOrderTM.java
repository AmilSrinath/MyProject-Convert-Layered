package lk.ijse.millmaster.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderTM {
    private String pid;
    private String oid;
    private int quntity;
    private Double unitPrice;
    private Double total;
    private Button btn;
}
