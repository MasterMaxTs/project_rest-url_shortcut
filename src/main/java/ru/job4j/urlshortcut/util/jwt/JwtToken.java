package ru.job4j.urlshortcut.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static ru.job4j.urlshortcut.util.jwt.JwtConstants.*;

/**
 * Утилитарный класс JWT
 */
public class JwtToken {

    /**
     * Генерирует JWT, добавляет его в заголовок при ответе
     * @param authResult результат аутентификации в виде Authentication
     * @param response HttpServletResponse
     */
    public static void generateToken(Authentication authResult,
                                     HttpServletResponse response) {
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secret.getBytes()));
        response.addHeader(headerString, tokenPrefix + token);
    }
}
