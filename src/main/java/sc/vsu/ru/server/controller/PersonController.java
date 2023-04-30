package sc.vsu.ru.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sc.vsu.ru.server.data.dto.*;
import sc.vsu.ru.server.service.PersonService;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("/auth")
    public ResponseEntity<PersonDto> authorization(@RequestBody PersonAuthDto personAuthDto) {
        PersonDto person = personService.authorization(personAuthDto);
        if (person != null)
            return new ResponseEntity<>(person, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
