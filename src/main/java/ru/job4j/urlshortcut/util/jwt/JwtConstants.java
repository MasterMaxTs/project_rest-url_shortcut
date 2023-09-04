package ru.job4j.urlshortcut.util.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс используется для инициализации статических констант, используемых
 * в классах, отвечающих за безопасность приложения
 */
@Component
public class JwtConstants {

    /**
     * секретный ключ для генерации JWT
     */
    public static String secret;

    /**
     * время жизни JWT
     */
    public static long expirationTime;

    /**
     * токен префикс
     */
    public static String tokenPrefix;

    /**
     * название заголовка
     */
    public static String headerString;

    /**
     * Инициализация статической константы
     * значением из файла application-prod.properties
     * @param secret секретный ключ для генерации JWT
     */
    @Value("${jwt.authentication.filter.secret}")
    void setSecret(String secret) {
        JwtConstants.secret = secret;
    }

    /**
     * Инициализация статической константы
     * значением из файла application-prod.properties
     * @param expirationTime время жизни JWT
     */
    @Value("${jwt.authentication.filter.expiration-time-in-millis}")
    void setExpirationTime(String expirationTime) {
        JwtConstants.expirationTime = Long.parseLong(expirationTime);
    }

    /**
     * Инициализация статической константы
     * значением из файла application-prod.properties
     * @param tokenPrefix токен префикс
     */
    @Value("${jwt.authentication.filter.token-prefix}")
    void setTokenPrefix(String tokenPrefix) {
        JwtConstants.tokenPrefix = tokenPrefix;
    }

    /**
     * Инициализация статической константы
     * значением из файла application-prod.properties
     * @param headerString название заголовка
     */
    @Value("${jwt.authentication.filter.header-string}")
    void setHeaderString(String headerString) {
        JwtConstants.headerString = headerString;
    }
}
