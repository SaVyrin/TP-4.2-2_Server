package sc.vsu.ru.server.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PersonEntity {
    final private UUID id;
    final private String login;
    final private String password;
    final private String name;
    final private String lastname;
    final private String address;
}
