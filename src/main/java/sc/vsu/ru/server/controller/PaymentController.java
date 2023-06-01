package sc.vsu.ru.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sc.vsu.ru.server.data.dto.PaymentDto;
import sc.vsu.ru.server.data.dto.PersonGetDto;
import sc.vsu.ru.server.service.PaymentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
@Tag(name = "Платежи")
public class PaymentController {
    private PaymentService paymentService;

    @GetMapping("/payments")
    @Operation(summary = "Получение текущих платежей", description = "Метод возвращает список текущих счетов на оплату для указанного лицевого счета.")
    @ApiResponse(responseCode = "200", description = "Платежи найдены")
    public ResponseEntity<List<PaymentDto>> getPayments(@Parameter(name = "Лицевой счет") @RequestParam("personalAccount") Integer personalAccount) {
        return new ResponseEntity<>(paymentService.getPayments(personalAccount), HttpStatus.OK);
    }

    @GetMapping("/expected")
    @Operation(summary = "Получение ожидаемого платежа", description = "Метод возвращает ожидаемый размер платежа на следующий расчетный период для указанного лицевого счета.")
    @ApiResponse(responseCode = "200", description = "Ожидаемый платеж расчитан")
    public ResponseEntity<Integer> getExpectedPayment(@Parameter(name = "Лицевой счет") @RequestParam("personalAccount") Integer personalAccount) {
        return new ResponseEntity<>(paymentService.getExpectedPayment(personalAccount), HttpStatus.OK);
    }

    @PostMapping("/pay")
    @Operation(summary = "Оплата счетов", description = "Метод осуществляет оплату всех неоплаченных счетов на данный момент для указанного лицевого счета.")
    @ApiResponse(responseCode = "200", description = "Счета оплачены")
    public void pay(@RequestBody PersonGetDto personGetDto) {
        paymentService.pay(personGetDto.getPersonalAccount());
    }
}
