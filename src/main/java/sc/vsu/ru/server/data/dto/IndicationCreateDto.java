package sc.vsu.ru.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class IndicationCreateDto {
    final private UUID ipuId;
    final private int value;
}
