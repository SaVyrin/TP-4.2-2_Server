package sc.vsu.ru.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonAuthDto {
    final private String login;
    final private String password;
}
