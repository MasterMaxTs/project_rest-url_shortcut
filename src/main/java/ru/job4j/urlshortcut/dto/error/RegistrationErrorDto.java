package ru.job4j.urlshortcut.dto.error;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO ошибка регистрации в приложении
 */
@Data
@NoArgsConstructor
public class RegistrationErrorDto  {

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

    /**
     * Сообщение ошибки пользователю в ответе
     */
    private String message;

    /**
     * Конструктор
     * @param message сообщение ошибки пользователю в ответе
     */
    public RegistrationErrorDto(String message) {
        this.message = message;
    }
}
