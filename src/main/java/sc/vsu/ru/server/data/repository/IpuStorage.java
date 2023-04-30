package sc.vsu.ru.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IpuStorage extends JpaRepository<IpuEntity, Integer> {
    List<IpuEntity> findByPerson(PersonEntity person);
    Optional<IpuEntity> findById(Integer id);
}
