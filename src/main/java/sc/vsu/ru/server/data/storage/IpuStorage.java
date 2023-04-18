package sc.vsu.ru.server.data.storage;

import org.springframework.stereotype.Repository;
import sc.vsu.ru.server.data.entity.IpuEntity;

import java.util.*;

@Repository
public class IpuStorage {
    private final Map<UUID, IpuEntity> ipus = new HashMap<>();

    public void addIpu(IpuEntity ipuEntity){
        ipus.put(ipuEntity.getId(), ipuEntity);
    }

    public List<IpuEntity> getIpuIds(){
        return new ArrayList<>(ipus.values());
    }

    public String getType(UUID id){
        return ipus.get(id).getType();
    }

    public List<UUID> getIpuIds(UUID personId){
        List<UUID> ipuIds = new ArrayList<>();
        for (Map.Entry<UUID, IpuEntity> ipu : ipus.entrySet()) {
            if (ipu.getValue().getPersonId().equals(personId)) {
                ipuIds.add(ipu.getValue().getId());
            }
        }
        return ipuIds;
    }
}
