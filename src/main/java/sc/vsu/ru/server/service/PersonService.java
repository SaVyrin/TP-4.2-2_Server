package sc.vsu.ru.server.service;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.PersonAuthDto;
import sc.vsu.ru.server.data.dto.PersonDto;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.mapper.PersonMapper;
import sc.vsu.ru.server.data.repository.PersonRepository;

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;
    private PersonMapper personMapper;

    @Transactional
    public PersonDto authorization(PersonAuthDto personAuthDto) {
        PersonEntity person = personRepository.findPerson(Integer.parseInt(personAuthDto.getLogin()), personAuthDto.getPassword());
        if (person != null)
            return personMapper.toDto(person);
        else
            return null;
    }
}
