package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(name = "IndicationWrapper", description = "Оберточный класс для списка IndicationCreateDto")
public class IndicationWrapper {
    private List<IndicationCreateDto> indications;
}
