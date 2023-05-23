package sc.vsu.ru.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sc.vsu.ru.server.data.dto.IndicationCreateDto;
import sc.vsu.ru.server.data.dto.IndicationDto;
import sc.vsu.ru.server.data.dto.IndicationWrapper;
import sc.vsu.ru.server.service.IndicationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ipu")
@Tag(name = "Показания")
public class IndicationController {
    @Autowired
    private IndicationService indicationService;

    @GetMapping("/current")
    @Operation(summary = "Получение текущих показаний", description = "Метод возвращает список текущих показаний для указанного лицевого счета.")
    @ApiResponse(responseCode = "200", description = "Показания найдены")
    public ResponseEntity<List<IndicationDto>> getCurrentIndications(@Parameter(name = "Лицевой счет") @RequestParam("personalAccount") Integer personalAccount){
        return new ResponseEntity<>(indicationService.getCurrentIndications(personalAccount), HttpStatus.OK);
    }

    @PostMapping("/send")
    @Operation(summary = "Передача показаний", description = "Метод сохраняет переданные для каждого ИПУ показания.")
    @ApiResponse(responseCode = "200", description = "Показания сохранены")
    public void addIndications(@RequestBody IndicationWrapper wrapper){
        List<IndicationCreateDto> indications = new ArrayList<>(wrapper.getIndications());
        indicationService.addIndications(indications);
    }
}
