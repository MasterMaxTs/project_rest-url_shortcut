package ru.job4j.urlshortcut.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Credential;

/**
 * Реализация сервиса Аутентификации
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Зависимость от AuthenticationManager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Выполняет процедуру аутентификации, генерирует токен, добавляя его
     * в заголовок в ответе
     * @param authManager AuthenticationManager
     * @param credentials объект Registration
     */
    @Transactional
    @Override
    public Authentication authenticate(AuthenticationManager authManager,
                             Credential credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getLogin(),
                        credentials.getPassword()
                ));
    }
}
