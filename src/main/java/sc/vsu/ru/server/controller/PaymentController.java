package sc.vsu.ru.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sc.vsu.ru.server.data.dto.PaymentDto;
import sc.vsu.ru.server.data.dto.PersonGetDto;
import sc.vsu.ru.server.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDto>> getPayments(@RequestBody PersonGetDto personGetDto){
        return new ResponseEntity<>(paymentService.getPayments(personGetDto.getPersonalAccount()), HttpStatus.OK);
    }

    @GetMapping("/expected")
    public ResponseEntity<Integer> getExpectedPayment(@RequestBody PersonGetDto personGetDto){
        return new ResponseEntity<>(paymentService.getExpectedPayment(personGetDto.getPersonalAccount()), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public void pay(@RequestBody PersonGetDto personGetDto){
        paymentService.pay(personGetDto.getPersonalAccount());
    }
}
