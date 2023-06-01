package sc.vsu.ru.server.service;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.IndicationCreateDto;
import sc.vsu.ru.server.data.dto.IndicationDto;
import sc.vsu.ru.server.data.entity.IndicationEntity;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.repository.IndicationRepository;
import sc.vsu.ru.server.data.repository.IpuRepository;
import sc.vsu.ru.server.data.repository.PersonRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class IndicationService {
    private PersonRepository personRepository;
    private IndicationRepository indicationRepository;
    private IpuRepository ipuRepository;

    @Transactional
    public List<IndicationDto> getCurrentIndications(Integer personalAccount) {
        PersonEntity person = personRepository.findByPersonalAccount(personalAccount);
        List<IpuEntity> ipus = ipuRepository.findByPerson(person);

        List<IndicationDto> indications = new ArrayList<>();

        for (IpuEntity ipu : ipus) {
            IndicationEntity indication = indicationRepository.findTopByIpuOrderByDateDesc(ipu);
            if (indication != null)
                indications.add(new IndicationDto(ipu.getId(), ipu.getType(), indication.getValue(), indication.getDate().toString()));
        }
        return indications;
    }

    @Transactional
    public void addIndications(List<IndicationCreateDto> indications) {
        for (IndicationCreateDto indicationDto : indications) {
            int tariff = 50;
            IpuEntity ipu = ipuRepository.findIpuEntityById(indicationDto.getIpuId());
            String type = ipu.getType();

            switch (type) {
                case "Горячая вода":
                    tariff = 100;
                    break;
                case "Холодная вода":
                    tariff = 75;
                    break;
                case "Электроэнергия":
                    tariff = 20;
                    break;
            }
            IndicationEntity previousIndication = indicationRepository.findTopByIpuOrderByDateDesc(ipu);
            int payment = (indicationDto.getValue() - previousIndication.getValue()) * tariff;

            IndicationEntity indication = new IndicationEntity();
            indication.setIpu(ipu);
            indication.setValue(indicationDto.getValue());
            indication.setPaymentValue(payment);
            indication.setPaid(false);
            indicationRepository.save(indication);
        }
    }
}
