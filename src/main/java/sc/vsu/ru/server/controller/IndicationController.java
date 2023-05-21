package sc.vsu.ru.server.controller;

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
public class IndicationController {
    @Autowired
    private IndicationService indicationService;

    @GetMapping("/current")
    public ResponseEntity<List<IndicationDto>> getCurrentIndications(@RequestParam("personalAccount") Integer personalAccount){
        return new ResponseEntity<>(indicationService.getCurrentIndications(personalAccount), HttpStatus.OK);
    }

    @PostMapping("/send")
    public void addIndications(@RequestBody IndicationWrapper wrapper){
        List<IndicationCreateDto> indications = new ArrayList<>(wrapper.getIndications());
        indicationService.addIndications(indications);
    }
}
