package sc.vsu.ru.server.data.storage;

import org.springframework.stereotype.Repository;
import sc.vsu.ru.server.data.dto.IndicationDto;
import sc.vsu.ru.server.data.entity.IndicationEntity;

import java.util.*;

@Repository
public class IndicationStorage {
    private final Map<UUID, IndicationEntity> indications = new HashMap<>();

    public void addIndication(IndicationEntity indicationEntity) {
        indications.put(indicationEntity.getId(), indicationEntity);
    }

    public List<IndicationEntity> getIndications() {
        return new ArrayList<>(indications.values());
    }

    public IndicationEntity getIndication(IndicationDto indicationDto) {
        for (Map.Entry<UUID, IndicationEntity> indication : indications.entrySet()) {
            if (indicationDto.getIpuId().equals(indication.getValue().getIpuId()) &&
            indicationDto.getDate().equals(indication.getValue().getDate()))
                    return indication.getValue();
        }
        return null;
    }

    public Map<UUID, IndicationEntity> getIndications(List<UUID> ipuIds, Date now) {
        Map<UUID, IndicationEntity> result = new HashMap<>();

        Calendar cal = new GregorianCalendar();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -1);

        for (Map.Entry<UUID, IndicationEntity> indication : indications.entrySet()) {
            if (ipuIds.contains(indication.getValue().getIpuId())) {
                if (indication.getValue().getDate().after(cal.getTime()) && indication.getValue().getDate().before(now))
                    result.put(indication.getValue().getIpuId(), indication.getValue());
            }
        }
        return result;
    }

    public IndicationEntity getPreviousIndication(UUID ipuId, Date now){
        Calendar cal = new GregorianCalendar();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -1);

        for (Map.Entry<UUID, IndicationEntity> indication : indications.entrySet()) {
            if (indication.getValue().getIpuId().equals(ipuId)) {
                if (indication.getValue().getDate().after(cal.getTime()) && indication.getValue().getDate().before(now))
                    return indication.getValue();
            }
        }
        return null;
    }
}
