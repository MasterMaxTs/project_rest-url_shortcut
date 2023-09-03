package ru.job4j.urlshortcut.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.dto.error.AppErrorDto;
import ru.job4j.urlshortcut.service.authentication.AuthenticationService;
import ru.job4j.urlshortcut.service.jwt.JwtService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Контроллер Аутентификации
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    /**
     * Зависимость от сервиса аутентификации
     */
    private final AuthenticationService authenticationService;

    /**
     * Зависимость от AuthenticationManager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Зависимость от сервиса JSON Web Token
     */
    private final JwtService jwtService;

    /**
     * Аутентифицирует пользователя по переданным регистрационным данным
     * в запросе, внедряет сгенерированный на основе данных результата
     * аутентификации токен в заголовок ответа
     * @param credential регистрационные данные на входе
     * @param response HttpServletResponse
     */
    @PostMapping("login")
    public void signIn(@Valid @RequestBody Credential credential,
                                    HttpServletResponse response) {
        Authentication authResult = authenticationService.authenticate(
                                            authenticationManager, credential);
        jwtService.createToken(authResult, response);
    }

    /**
     * Обрабатывает исключения типа BadCredentialsException, которые
     * могут возникнуть в контроллере вследствие неуспешной аутентификации
     * @param ex Exception
     * @return объект ResponseEntity со статусом 404 и телом в виде
     * объекта-ошибки BaseErrorDto
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        String errorMessage = "Registration data is incorrect!";
        log.error("Attempt to login to the application with incorrect credentials");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppErrorDto(
                                HttpStatus.NOT_FOUND.value(),
                                errorMessage,
                                ex.getClass().getName()));
    }
}
