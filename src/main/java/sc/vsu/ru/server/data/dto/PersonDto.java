package sc.vsu.ru.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonDto {
    private final Integer personalAccount;
    private final String name;
    private final String surname;
    private final String address;
}
