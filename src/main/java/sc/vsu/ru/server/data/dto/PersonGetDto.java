package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "PaymentRequest", description = "DTO для оплаты по данным пользователя")
public class PersonGetDto {
    @Schema(name = "personalAccount", description = "Лицевой счет", example = "12345")
    private Integer personalAccount;
}
