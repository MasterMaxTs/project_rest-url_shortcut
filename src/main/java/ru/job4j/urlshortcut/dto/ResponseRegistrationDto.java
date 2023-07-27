package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO регистрация сайта в виде ответа пользователю
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRegistrationDto {

    /**
     * Флаг регистрации сайта в приложении
     */
    private boolean registration;

    /**
     * Логин пользователя
     */
    private String login;

    /**
     * Пароль пользователя
     */
    private String password;
}
