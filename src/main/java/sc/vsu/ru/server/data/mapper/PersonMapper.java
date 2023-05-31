package sc.vsu.ru.server.data.mapper;

import org.springframework.stereotype.Component;
import sc.vsu.ru.server.data.dto.PersonDto;
import sc.vsu.ru.server.data.entity.PersonEntity;

@Component
public class PersonMapper {

    public PersonDto toDto(PersonEntity personEntity) {
        Integer personalAccount = personEntity.getPersonalAccount();
        String name = personEntity.getName();
        String surname = personEntity.getSurname();
        String address = personEntity.getAddress();

        return new PersonDto(personalAccount, name, surname, address);
    }
}
