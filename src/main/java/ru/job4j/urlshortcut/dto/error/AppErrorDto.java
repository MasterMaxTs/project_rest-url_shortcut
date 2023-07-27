package ru.job4j.urlshortcut.dto.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO ошибка приложения
 */
@Data
@NoArgsConstructor
public class AppErrorDto {

    /**
     * Числовое значение статуса ответа
     */
    private int status;

    /**
     * Сообщение ошибки пользователю в ответе
     */
    private String message;

    /**
     * Тип ошибки
     */
    private String type;

    /**
     * Время выхода ошибки
     */
    private Date timestamp;

    /**
     * Конструктор
     * @param status числовое значение статуса ответа
     * @param message сообщение ошибки пользователю в ответе
     * @param type тип ошибки
     */
    public AppErrorDto(int status, String message, String type) {
        this.status = status;
        this.message = message;
        this.type = type;
        timestamp = new Date();
    }
}
