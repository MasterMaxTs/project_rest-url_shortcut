package ru.job4j.urlshortcut.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.dto.error.RegistrationErrorDto;
import ru.job4j.urlshortcut.dto.request.RequestSiteDto;
import ru.job4j.urlshortcut.mapper.RegistrationConverter;
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
     * Зависимость от RegistrationConverter
     */
    private final RegistrationConverter converter;

    /**
     * Выполняет регистрацию сайта в приложении по его доменному имени
     * @param siteDto объект RegistrationSiteDto на входе
     * @return объект ResponseEntity со статусом 201 и телом виде
     * объекта ResponseRegistrationDto, если регистрация сайта прошла успешно,
     * иначе - объект ResponseEntity со статусом 400  телом виде объекта
     * RegistrationErrorDto
     */
    @PostMapping("register")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestSiteDto siteDto) {
        String errorMessage = "The site with this domain name is already "
                + "registered in the application.";
        String domainName = siteDto.getSite();
        Site site = registrationService.register(domainName);
        return site.isRegistration()
                ? ResponseEntity.status(HttpStatus.CREATED)
                    .body(converter.convertToDto(site))
                : ResponseEntity.badRequest()
                    .body(new RegistrationErrorDto(errorMessage));
    }
}
