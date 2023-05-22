package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "PersonDto", description = "DTO для пользователя")
public class PersonDto {
    @Schema(name = "personalAccount", description = "Лицевой счет", example = "12345")
    private final Integer personalAccount;
    @Schema(name = "name", description = "Имя", example = "Иван")
    private final String name;
    @Schema(name = "surname", description = "Фамилия", example = "Иванов")
    private final String surname;
    @Schema(name = "address", description = "Адрес", example = "г. Воронеж, ул. Ленина, д. 13, кв. 280")
    private final String address;
}
