package sc.vsu.ru.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class IndicationDto {
    final private UUID ipuId;
    final private String type;
    final private int value;
    final private Date date;
}
