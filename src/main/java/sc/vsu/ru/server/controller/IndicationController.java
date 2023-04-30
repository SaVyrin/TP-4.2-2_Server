package sc.vsu.ru.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.vsu.ru.server.data.dto.IndicationCreateDto;
import sc.vsu.ru.server.data.dto.IndicationDto;
import sc.vsu.ru.server.data.dto.IndicationWrapper;
import sc.vsu.ru.server.data.dto.PersonGetDto;
import sc.vsu.ru.server.service.IndicationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ipu")
public class IndicationController {
    @Autowired
    private IndicationService indicationService;
    @PostMapping("/current")
    public ResponseEntity<List<IndicationDto>> getCurrentIndications(@RequestBody PersonGetDto personGetDto){
        return new ResponseEntity<>(indicationService.getCurrentIndications(personGetDto.getPersonalAccount()), HttpStatus.OK);
    }

    @PostMapping("/send")
    public void addIndications(@RequestBody IndicationWrapper wrapper){
        List<IndicationCreateDto> indications = new ArrayList<>(wrapper.getIndications());
        indicationService.addIndications(indications);
    }
}
