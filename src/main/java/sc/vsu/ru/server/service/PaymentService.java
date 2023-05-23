package sc.vsu.ru.server.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sc.vsu.ru.server.data.dto.PaymentDto;
import sc.vsu.ru.server.data.entity.IndicationEntity;
import sc.vsu.ru.server.data.entity.IpuEntity;
import sc.vsu.ru.server.data.entity.PersonEntity;
import sc.vsu.ru.server.data.repository.IndicationStorage;
import sc.vsu.ru.server.data.repository.IpuStorage;
import sc.vsu.ru.server.data.repository.PersonStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private IndicationStorage indicationStorage;
    @Autowired
    private IpuStorage ipuStorage;
    @Autowired
    private PersonStorage personStorage;

    @Transactional
    public List<PaymentDto> getPayments(Integer personalAccount) {
        List<PaymentDto> payments = new ArrayList<>();
        List<IpuEntity> ipus = getIpusByPerson(personalAccount);
        for (IpuEntity ipu : ipus){
            Integer paymentValue = indicationStorage.getSumOfAllUnpaidIndications(ipu);
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
            List<IndicationEntity> indications = indicationStorage.findTop5ByIpuOrderByDateDesc(ipu);
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
            indicationStorage.setPaid(ipu);
        }
    }

    private List<IpuEntity> getIpusByPerson(Integer personalAccount){
        PersonEntity person = personStorage.findByPersonalAccount(personalAccount);
        return ipuStorage.findByPerson(person);
    }
}
