package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "PaymentDto", description = "DTO для платежа")
public class PaymentDto {
    @Schema(name = "type", description = "Тип ИПУ", example = "Горяча вода")
    private final String type;
    @Schema(name = "value", description = "Величина платежа", example = "100")
    private final int value;
}
