package sc.vsu.ru.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class IpuDto {
    final private UUID id;
    final private UUID personId;
    final private String type;
}
