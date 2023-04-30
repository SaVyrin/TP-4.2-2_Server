package sc.vsu.ru.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.PersonAuthDto;
import sc.vsu.ru.server.data.dto.PersonDto;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.repository.PersonStorage;


@Service
public class PersonService {
    @Autowired
    private PersonStorage personStorage;

    public PersonDto authorization(PersonAuthDto personAuthDto) {
        PersonEntity person = personStorage.findByPersonalAccountAndPassword(personAuthDto.getLogin(), personAuthDto.getPassword());
        if (person != null)
            return new PersonDto(person.getPersonalAccount(), person.getName(), person.getSurname(), person.getAddress());
        else
            return null;
    }
}
