package sc.vsu.ru.server.data.storage;

import org.springframework.stereotype.Repository;
import sc.vsu.ru.server.data.dto.PersonAuthDto;
import sc.vsu.ru.server.data.entity.PersonEntity;

import java.util.*;

@Repository
public class PersonStorage {
    final private Map<UUID, PersonEntity> persons = new HashMap<>();

    public void addPerson(PersonEntity personEntity){
        persons.put(personEntity.getId(), personEntity);
    }

    public List<PersonEntity> getPersons(){
        return new ArrayList<>(persons.values());
    }

    public PersonEntity getPerson(PersonAuthDto personAuthDto){
        for (Map.Entry<UUID, PersonEntity> person : persons.entrySet()) {
            if (person.getValue().getLogin().equals(personAuthDto.getLogin())
            && person.getValue().getPassword().equals(personAuthDto.getPassword())) {
                return persons.get(person.getKey());
            }
        }
        return null;
    }
}
