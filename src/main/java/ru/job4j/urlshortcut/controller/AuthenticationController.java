package ru.job4j.urlshortcut.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.dto.error.AppErrorDto;
import ru.job4j.urlshortcut.service.authentication.AuthenticationService;

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
     * Аутентифицирует пользователя по переданным регистрационным данным
     * в запросе
     * @param credential регистрационные данные на входе
     * @param response HttpServletResponse
     */
    @PostMapping("login")
    public void signIn(@Valid @RequestBody Credential credential,
                                    HttpServletResponse response) {
        authenticationService.authenticate(
                authenticationManager, credential, response);
    }

    /**
     * Обрабатывает исключения типа BadCredentialsException, которые
     * могут возникнуть в контроллере вследствие неуспешной аутентификации
     * @param ex Exception
     * @param response HttpServletResponse
     * @return объект ResponseEntity со статусом 404 и телом в виде
     * объекта-ошибки BaseErrorDto
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<?> handleException(Exception ex,
                                             HttpServletResponse response) {
        String errorMessage = "Registration data is incorrect!";
        response.setStatus(HttpStatus.NOT_FOUND.value());
        log.error("Attempt to login to the application with incorrect credentials");
        return ResponseEntity.status(response.getStatus())
                .body(new AppErrorDto(
                                response.getStatus(),
                                errorMessage,
                                ex.getClass().getName()));
    }
}
