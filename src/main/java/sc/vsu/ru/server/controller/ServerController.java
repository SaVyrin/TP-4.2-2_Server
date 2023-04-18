package sc.vsu.ru.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sc.vsu.ru.server.data.dto.*;
import sc.vsu.ru.server.service.ServerService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("")
public class ServerController {
    private ServerService serverService;

    //просмотр данных из таблиц
    @GetMapping("/check/persons")
    public List<PersonDto> getPersons(){
        return serverService.getPersons();
    }

    @GetMapping("/check/ipus")
    public List<IpuDto> getIpus(){
        return serverService.getIpus();
    }

    @GetMapping("/check/indications")
    public List<IndicationDto> getIndications(){
        return serverService.getIndications();
    }

    @GetMapping("/check/payments")
    public List<PaymentDto> getPayments(){
        return serverService.getPayments();
    }

    //запросы, которые нужны
    @PostMapping("/auth")
    public ResponseEntity<PersonDto> authorization(@RequestBody PersonAuthDto personAuthDto) {
        PersonDto person = serverService.authorization(personAuthDto);
        if (person != null)
            return new ResponseEntity<>(person, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/ipu/current")
    public ResponseEntity<List<IndicationDto>> getCurrentIndications(@RequestBody UUID personalAccount){
        return new ResponseEntity<>(serverService.getCurrentIndications(personalAccount), HttpStatus.OK);
    }

    @PostMapping("/ipu/send")
    public void addIndications(@RequestBody IndicationWrapper wrapper){
        List<IndicationCreateDto> indications = new ArrayList<>(wrapper.getIndications());
        serverService.addIndications(indications);
    }

    @GetMapping("/payment/payments")
    public List<PaymentDto> getPayment(@RequestBody UUID personalAccount){
        return serverService.getPayments(personalAccount);
    }

    @GetMapping("/payment/expected")
    public int getExpectedPayment(@RequestBody UUID personId){
        return serverService.getExpectedPayment(personId);
    }

    @PostMapping("/payment/pay")
    public void pay(@RequestBody UUID personId){
        serverService.pay(personId);
    }

    //заполнение данных в таблицы
    @GetMapping("/init")
    public void init(){
        serverService.init();
    }
}
