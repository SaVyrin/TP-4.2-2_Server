package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "IndicationDto", description = "DTO для показаний")
public class IndicationDto {
    @Schema(name = "ipuId", description = "id ИПУ", example = "1")
    private final Integer ipuId;
    @Schema(name = "type", description = "Тип ИПУ", example = "Горяча вода")
    private final String type;
    @Schema(name = "value", description = "Величина показания", example = "295")
    private final int value;
    @Schema(name = "date", description = "Дата", example = "2023-05-22 01:42:39.802")
    private final String date;
}
