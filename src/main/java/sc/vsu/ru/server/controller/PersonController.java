package sc.vsu.ru.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sc.vsu.ru.server.data.dto.*;
import sc.vsu.ru.server.service.PersonService;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Пользователи")
public class PersonController {
    private PersonService personService;

    @PostMapping
    @Operation(summary = "Авторизация", description = "Метод осуществляет авторизацию пользователя на основе лицевого счета и пароля.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
            @ApiResponse(responseCode = "400", description = "Некорректные учетные данные", content = @Content)
    })
    public ResponseEntity<PersonDto> authorization(@RequestBody PersonAuthDto personAuthDto) {
        PersonDto person = personService.authorization(personAuthDto);
        if (person != null)
            return new ResponseEntity<>(person, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
