package sc.vsu.ru.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sc.vsu.ru.server.data.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    @Query("SELECT p FROM PersonEntity p WHERE p.personalAccount = :login AND p.password = function('md5', :password)")
    PersonEntity findPerson(@Param("login")Integer login, @Param("password")String password);
    PersonEntity findByPersonalAccount(Integer personalAccount);
}
