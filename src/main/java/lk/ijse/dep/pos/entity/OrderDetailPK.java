package lk.ijse.dep.pos.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailPK implements Serializable {

    private String orderId;
    private String itemCode;

}
