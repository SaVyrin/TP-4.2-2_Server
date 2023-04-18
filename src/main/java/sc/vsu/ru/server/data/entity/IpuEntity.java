package sc.vsu.ru.server.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class IpuEntity {
    final private UUID id;
    final private UUID personId;
    final private String type;
}
