package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(name = "PersonAuthDto", description = "DTO для авторизации пользователя")
public class PersonAuthDto {
    @Schema(name = "login", description = "Логин", example = "12345")
    private final String login;
    @Schema(name = "password", description = "Пароль", example = "password")
    private final String password;
}
