package sc.vsu.ru.server.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.IndicationCreateDto;
import sc.vsu.ru.server.data.dto.IndicationDto;
import sc.vsu.ru.server.data.entity.IndicationEntity;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.repository.IndicationStorage;
import sc.vsu.ru.server.data.repository.IpuStorage;
import sc.vsu.ru.server.data.repository.PersonStorage;

import java.util.*;

@Service
public class IndicationService {
    @Autowired
    private PersonStorage personStorage;
    @Autowired
    private IndicationStorage indicationStorage;
    @Autowired
    private IpuStorage ipuStorage;

    @Transactional
    public List<IndicationDto> getCurrentIndications(Integer personalAccount) {
        PersonEntity person = personStorage.findByPersonalAccount(personalAccount);
        List<IpuEntity> ipus = ipuStorage.findByPerson(person);

        List<IndicationDto> indications = new ArrayList<>();

        for (IpuEntity ipu : ipus) {
            IndicationEntity indication = indicationStorage.findLastIndication(ipu);
            if (indication != null)
                indications.add(new IndicationDto(ipu.getId(), ipu.getType(), indication.getValue(), indication.getDate().toString()));
        }
        return indications;
    }

    @Transactional
    public void addIndications(List<IndicationCreateDto> indications) {
        for (IndicationCreateDto indicationDto : indications) {
            int tariff = 50;
            Optional<IpuEntity> ipu = ipuStorage.findById(indicationDto.getIpuId());

            if (ipu.isPresent()) {
                switch (ipu.get().getType()) {
                    case "Горячая вода" -> tariff = 100;
                    case "Холодная вода" -> tariff = 75;
                    case "Электричество" -> tariff = 20;
                }
                IndicationEntity previousIndication = indicationStorage.findLastIndication(ipu.get());
                int payment = (indicationDto.getValue() - previousIndication.getValue()) * tariff;
                IndicationEntity indication = new IndicationEntity();
                indication.setIpu(ipu.get());
                indication.setValue(indicationDto.getValue());
                indication.setPaymentValue(payment);
                indication.setPaid(false);
                indicationStorage.save(indication);
            }
        }
    }

}
