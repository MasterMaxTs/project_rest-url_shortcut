package ru.job4j.urlshortcut.service.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import ru.job4j.urlshortcut.domain.Credential;

/**
 * Сервис Аутентификации
 */
public interface AuthenticationService {

    /**
     * Выполняет процедуру аутентификации
     * @param authManager AuthenticationManager
     * @param credentials объект Registration
     * @return объект результата аутентификации
     */
    Authentication authenticate(AuthenticationManager authManager,
                                Credential credentials);
}
