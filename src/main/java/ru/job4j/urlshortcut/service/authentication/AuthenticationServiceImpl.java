package ru.job4j.urlshortcut.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.util.jwt.JwtToken;

import javax.servlet.http.HttpServletResponse;

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
     * @param response HttpServletResponse
     */
    @Transactional
    @Override
    public void authenticate(AuthenticationManager authManager,
                             Credential credentials,
                             HttpServletResponse response) {
        Authentication authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getLogin(),
                        credentials.getPassword()
                ));
        JwtToken.generateToken(authResult, response);
    }
}
