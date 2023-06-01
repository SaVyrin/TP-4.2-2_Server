package sc.vsu.ru.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.vsu.ru.server.data.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findByPersonalAccountAndPassword(Integer login, String password);
    PersonEntity findByPersonalAccount(Integer personalAccount);
}
