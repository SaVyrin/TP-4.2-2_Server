package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "IndicationRequest", description = "DTO для передачи показаний")
public class IndicationCreateDto {
    @Schema(name = "ipuId", description = "id ИПУ", example = "1")
    private final Integer ipuId;
    @Schema(name = "value", description = "Величина показания", example = "295")
    private final int value;
}
