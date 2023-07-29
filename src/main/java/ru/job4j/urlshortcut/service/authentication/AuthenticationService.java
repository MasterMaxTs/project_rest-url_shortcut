package ru.job4j.urlshortcut.service.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import ru.job4j.urlshortcut.domain.Credential;

import javax.servlet.http.HttpServletResponse;

/**
 * Сервис Аутентификации
 */
public interface AuthenticationService {

    /**
     * Выполняет процедуру аутентификации
     * @param authManager AuthenticationManager
     * @param credentials объект Registration
     * @param response HttpServletResponse
     */
    void authenticate(AuthenticationManager authManager,
                      Credential credentials,
                      HttpServletResponse response);
}
