package sc.vsu.ru.server.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "IndicationWrapper", description = "Оберточный класс для списка IndicationCreateDto")
public class IndicationWrapper {
    private List<IndicationCreateDto> indications;

    public List<IndicationCreateDto> getIndications() {
        return indications;
    }
}
