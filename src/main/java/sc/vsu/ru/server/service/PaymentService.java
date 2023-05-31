package sc.vsu.ru.server.service;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.PaymentDto;
import sc.vsu.ru.server.data.entity.IndicationEntity;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.repository.IndicationRepository;
import sc.vsu.ru.server.data.repository.IpuRepository;
import sc.vsu.ru.server.data.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private IndicationRepository indicationRepository;
    private IpuRepository ipuRepository;
    private PersonRepository personRepository;

    @Transactional
    public List<PaymentDto> getPayments(Integer personalAccount) {
        List<IpuEntity> ipus = getIpusByPerson(personalAccount);

        List<PaymentDto> payments = new ArrayList<>();

        for (IpuEntity ipu : ipus){
            Integer paymentValue = indicationRepository.getSumOfAllUnpaidIndications(ipu);

            if (paymentValue == null)
                payments.add(new PaymentDto(ipu.getType(), 0));
            else
                payments.add(new PaymentDto(ipu.getType(), paymentValue));
        }
        return payments;
    }

    @Transactional
    public int getExpectedPayment(Integer personalAccount) {
        int expectedPayment = 0;
        int numberOfPayments = 0;

        List<IpuEntity> ipus = getIpusByPerson(personalAccount);

        for (IpuEntity ipu : ipus){
            List<IndicationEntity> indications = indicationRepository.findTop5ByIpuOrderByDateDesc(ipu);

            for (IndicationEntity indication : indications){
                if (indication.getPaymentValue() != 0) {
                    expectedPayment += indication.getPaymentValue();
                    numberOfPayments++;
                }
            }
        }
        return expectedPayment/numberOfPayments;
    }

    @Transactional
    public void pay(Integer personalAccount) {
        List<IpuEntity> ipus = getIpusByPerson(personalAccount);

        for (IpuEntity ipu : ipus){
            indicationRepository.setPaid(ipu);
        }
    }

    private List<IpuEntity> getIpusByPerson(Integer personalAccount){
        PersonEntity person = personRepository.findByPersonalAccount(personalAccount);
        return ipuRepository.findByPerson(person);
    }
}
