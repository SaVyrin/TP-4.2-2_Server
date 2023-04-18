package sc.vsu.ru.server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.*;
import sc.vsu.ru.server.data.entity.IndicationEntity;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.storage.IndicationStorage;
import sc.vsu.ru.server.data.storage.IpuStorage;
import sc.vsu.ru.server.data.storage.PersonStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServerService {
    final private PersonStorage personStorage;
    final private IpuStorage ipuStorage;
    final private IndicationStorage indicationStorage;

    //просмотр данных из таблиц
    public List<PersonDto> getPersons() {
        return personStorage.getPersons()
                .stream()
                .map(person -> new PersonDto(person.getId(), person.getName(), person.getLastname(), person.getAddress()))
                .collect(Collectors.toList());
    }

    public List<IpuDto> getIpus() {
        return ipuStorage.getIpuIds()
                .stream()
                .map(ipu -> new IpuDto(ipu.getId(), ipu.getPersonId(), ipu.getType()))
                .collect(Collectors.toList());
    }

    public List<IndicationDto> getIndications() {
        return indicationStorage.getIndications()
                .stream()
                .map(indication -> new IndicationDto(indication.getIpuId(), ipuStorage.getType(indication.getIpuId()), indication.getValue(), indication.getDate()))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> getPayments() {
        return indicationStorage.getIndications()
                .stream()
                .map(payment -> new PaymentDto(ipuStorage.getType(payment.getIpuId()), payment.getPaymentValue()))
                .collect(Collectors.toList());
    }

    //запросы, которые нужны
    public PersonDto authorization(PersonAuthDto personAuthDto) {
        PersonEntity person = personStorage.getPerson(personAuthDto);
        if (person != null)
            return new PersonDto(person.getId(), person.getName(), person.getLastname(), person.getAddress());
        else
            return null;
    }

    public List<IndicationDto> getCurrentIndications(UUID personalAccount) {
        List<IndicationDto> currentIndications = new ArrayList<>();
        List<UUID> ipuIds = ipuStorage.getIpuIds(personalAccount);

        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        while (currentIndications.size() == 0){
            Date now = cal.getTime();
            currentIndications = getLastMonthIndications(ipuIds, now);
            cal.add(Calendar.MONTH, -1);
        }
        return currentIndications;
    }

    private List<IndicationDto> getLastMonthIndications(List<UUID> ipuIds, Date now) {
        Map<UUID, IndicationEntity> lastMonthIndications = indicationStorage.getIndications(ipuIds, now);
        List<IndicationDto> lastMonthIndicationsDto = new ArrayList<>();
        for (Map.Entry<UUID, IndicationEntity> entry : lastMonthIndications.entrySet()) {
            IndicationEntity indication = entry.getValue();
            lastMonthIndicationsDto.add(new IndicationDto(indication.getIpuId(), ipuStorage.getType(indication.getIpuId()), indication.getValue(), indication.getDate()));
        }
        return lastMonthIndicationsDto;
    }

    public void addIndications(List<IndicationCreateDto> indicationCreateDto) {
        for (IndicationCreateDto indicationDto : indicationCreateDto) {
            int tariff = 100;
            Calendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -1);

            IndicationEntity previousIndication = indicationStorage.getPreviousIndication(indicationDto.getIpuId(), cal.getTime());
            if (previousIndication != null) {
                int payment = (indicationDto.getValue() - previousIndication.getValue()) * tariff;
                IndicationEntity currentEntity = new IndicationEntity(UUID.randomUUID(), indicationDto.getIpuId(), indicationDto.getValue(), new Date(), UUID.randomUUID(), payment, false);
                indicationStorage.addIndication(currentEntity);
            } else {
                int payment = indicationDto.getValue() * tariff;
                IndicationEntity currentEntity = new IndicationEntity(UUID.randomUUID(), indicationDto.getIpuId(), indicationDto.getValue(), new Date(), UUID.randomUUID(), payment, false);
                indicationStorage.addIndication(currentEntity);
            }
        }
    }

    public List<PaymentDto> getPayments(UUID personalAccount) {
        List<IndicationDto> indications = getCurrentIndications(personalAccount);
        List<PaymentDto> payments = new ArrayList<>();

        for (IndicationDto indication : indications){
            IndicationEntity indicationEntity = indicationStorage.getIndication(indication);
            payments.add(new PaymentDto(indication.getType(), indicationEntity.getPaymentValue()));
        }
        return payments;
    }

    private int getPayment(UUID personalAccount, Date now) {
        List<UUID> ipuIds = ipuStorage.getIpuIds(personalAccount);
        Map<UUID, IndicationEntity> lastMonthIndications = indicationStorage.getIndications(ipuIds, now);

        int payment = 0;
        for (Map.Entry<UUID, IndicationEntity> indication : lastMonthIndications.entrySet()){
            payment += indication.getValue().getPaymentValue();
        }

        return payment;
    }

    public int getExpectedPayment(UUID personalAccount) {
        Date now = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(now);

        int expectedPayment = 0;
        int numberOfMonths = 0;
        for (int i = 0; i < 5; i++){
            int tempPayment = getPayment(personalAccount, cal.getTime());
            if (tempPayment != 0) {
                expectedPayment += tempPayment;
                numberOfMonths++;
            }
            cal.add(Calendar.MONTH, -1);
        }
        return expectedPayment/numberOfMonths;
    }

    public void pay(UUID personalAccount) {
        List<UUID> ipuIds = ipuStorage.getIpuIds(personalAccount);
        Map<UUID, IndicationEntity> lastMonthIndications = indicationStorage.getIndications(ipuIds, new Date());

        for (Map.Entry<UUID, IndicationEntity> indication : lastMonthIndications.entrySet()){
            indication.getValue().setPaid(true);
        }
    }

    //заполнение таблиц данными
    public void init() {
        //пользователь 1
        personStorage.addPerson(new PersonEntity(UUID.fromString("f95e060e-d1c8-4470-a125-8429ecd6164c"), "first", "pass1", "ivan", "ivanov", "ne vazhno"));

        //пользователь 2
        personStorage.addPerson(new PersonEntity(UUID.fromString("bb5b6e67-c980-4a5e-bb12-bcf47df3eca9"), "second", "pass2", "ivan2", "ivanov2", "ne vazhno2"));

        //ИПУ 1 пользователя 1
        ipuStorage.addIpu(new IpuEntity(UUID.fromString("b2f8fc68-93f5-4e1e-8c42-67575a2dce45"), UUID.fromString("f95e060e-d1c8-4470-a125-8429ecd6164c"), "hot water"));
        //ИПУ 2 пользователя 1
        ipuStorage.addIpu(new IpuEntity(UUID.fromString("3b81657a-3081-4534-9775-6a9a96f86920"), UUID.fromString("f95e060e-d1c8-4470-a125-8429ecd6164c"), "cold water"));

        //ИПУ 3 пользователя 2
        ipuStorage.addIpu(new IpuEntity(UUID.fromString("d5b8520a-dba9-11ed-afa1-0242ac120002"), UUID.fromString("bb5b6e67-c980-4a5e-bb12-bcf47df3eca9"), "electricity"));

        //Показание 1 ИПУ 1
        indicationStorage.addIndication(new IndicationEntity(UUID.fromString("3527b4bb-dbbd-48e6-bf28-d4f530e5b72b"), UUID.fromString("b2f8fc68-93f5-4e1e-8c42-67575a2dce45"),
                0, new Date(123, Calendar.FEBRUARY, 14, 1, 0), UUID.fromString("c11a6ddc-c77c-4058-90e0-d12d5dbef482"), 300, false));
        //Показание 2 ИПУ 1
        indicationStorage.addIndication(new IndicationEntity(UUID.fromString("e64ae6fa-5f7f-4a89-8aca-be4eeb6505c5"), UUID.fromString("b2f8fc68-93f5-4e1e-8c42-67575a2dce45"),
                10, new Date(123, Calendar.MARCH, 14, 1, 0), UUID.fromString("2f817d3d-b860-455e-a839-65e5ca5cf3f4"), 300, false));

        //Показание 3 ИПУ 2
        indicationStorage.addIndication(new IndicationEntity(UUID.fromString("85407807-a177-454f-afe6-f442054a81ed"), UUID.fromString("3b81657a-3081-4534-9775-6a9a96f86920"),
                2, new Date(123, Calendar.FEBRUARY, 14, 1, 0), UUID.fromString("61dfc553-a3b9-431f-969f-4d63ec456d8b"), 800, false));
        //Показание 4 ИПУ 2
        indicationStorage.addIndication(new IndicationEntity(UUID.fromString("2b4bc396-84b1-4d64-9aba-eae4d4976bf1"), UUID.fromString("3b81657a-3081-4534-9775-6a9a96f86920"),
                5, new Date(123, Calendar.MARCH, 14, 1, 0), UUID.fromString("5652afc1-9a9d-4742-a929-9d8dbd6ed1fd"), 800, false));
    }
}
