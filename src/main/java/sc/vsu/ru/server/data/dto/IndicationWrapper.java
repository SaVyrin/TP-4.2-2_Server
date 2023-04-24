package sc.vsu.ru.server.data.dto;

import java.util.List;

public class IndicationWrapper {
    private List<IndicationCreateDto> indications;

    public List<IndicationCreateDto> getIndications() {
        return indications;
    }

    public void setIndications(List<IndicationCreateDto> indications) {
        this.indications = indications;
    }
}
