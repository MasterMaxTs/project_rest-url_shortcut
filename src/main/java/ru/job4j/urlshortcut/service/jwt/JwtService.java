package ru.job4j.urlshortcut.service.jwt;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;

/**
 * Сервис JSON Web Token
 */
public interface JwtService {

    /**
     * Создаёт JWT, внедряет его в заголовок ответа
     * @param authResult результат аутентификации в виде Authentication
     * @param response HttpServletResponse
     */
    void createToken(Authentication authResult, HttpServletResponse response);
}
