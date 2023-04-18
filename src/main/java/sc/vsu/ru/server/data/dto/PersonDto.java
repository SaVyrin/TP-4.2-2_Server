package sc.vsu.ru.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PersonDto {
    final private UUID personalAccount;
    final private String name;
    final private String lastname;
    final private String address;
}
