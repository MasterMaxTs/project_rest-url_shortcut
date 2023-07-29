package ru.job4j.urlshortcut.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.dto.RequestSiteDto;
import ru.job4j.urlshortcut.dto.ResponseRegistrationDto;
import ru.job4j.urlshortcut.dto.error.RegistrationErrorDto;
import ru.job4j.urlshortcut.service.registration.RegistrationService;

import javax.validation.Valid;

/**
 * Контроллер Регистрации
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class RegistrationController {

    /**
     * Зависимость от сервиса регистрации
     */
    private final RegistrationService registrationService;

    /**
     * Выполняет регистрацию сайта в приложении по его доменному имени
     * @param siteDto объект RegistrationSiteDto на входе
     * @return объект ResponseEntity со статусом 201 и телом виде
     * объекта ResponseRegistrationDto
     */
    @PostMapping("register")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestSiteDto siteDto) {
        String domainName = siteDto.getSite();
        Site siteInDb = registrationService.register(domainName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseRegistrationDto(
                        siteInDb.isRegistration(),
                        siteInDb.getCredential().getLogin(),
                        siteInDb.getCredential().getPassword()
                ));
    }

    /**
     * Обрабатывает исключения типа ConstraintViolationException, которые
     * могут возникнуть в контроллере вследствие неуспешной регистрации
     * @return объект ResponseEntity со статусом 400 и телом в виде
     * объекта-ошибки RegistrationErrorDto
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> handleException() {
        String errorMessage = "The site with this domain name is already "
                + "registered in the application.";
        log.error("Attempt to register an already existing site domain name in the application");
        return ResponseEntity.badRequest()
                .body(new RegistrationErrorDto(errorMessage));
    }
}
