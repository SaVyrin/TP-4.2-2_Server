package sc.vsu.ru.server.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class IndicationEntity {
    final private UUID id;
    final private UUID ipuId;
    final private int value;
    final private Date date;
    final private UUID paymentId;
    final private int paymentValue;
    private boolean paid;
}
