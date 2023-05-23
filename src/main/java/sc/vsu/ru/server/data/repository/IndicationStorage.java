package sc.vsu.ru.server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sc.vsu.ru.server.data.entity.IndicationEntity;
import sc.vsu.ru.server.data.entity.IpuEntity;

import java.util.*;

@Repository
public interface IndicationStorage extends JpaRepository<IndicationEntity, Integer> {

    @Modifying
    @Query("update IndicationEntity indi set indi.paid = true where indi.ipu = :ipu")
    void setPaid(@Param("ipu") IpuEntity ipu);

    IndicationEntity findTopByIpuOrderByDateDesc(@Param("ipu") IpuEntity ipu);

    List<IndicationEntity> findTop5ByIpuOrderByDateDesc(@Param("ipu") IpuEntity ipu);

    @Query("select SUM(indi.paymentValue) from IndicationEntity indi where indi.ipu = :ipu and indi.paid = false")
    Integer getSumOfAllUnpaidIndications(@Param("ipu") IpuEntity ipu);


}
