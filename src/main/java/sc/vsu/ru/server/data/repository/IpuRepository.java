package sc.vsu.ru.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;

import java.util.List;

@Repository
public interface IpuRepository extends JpaRepository<IpuEntity, Integer> {
    List<IpuEntity> findByPerson(PersonEntity person);
    IpuEntity findIpuEntityById(Integer id);
}
