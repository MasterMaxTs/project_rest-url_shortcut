package ru.job4j.urlshortcut.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO Статистика в виде ответа пользователю
 */
@Data
@AllArgsConstructor
public class ResponseStatisticDto {

    /**
     * Имя ссылки URL
     */
    private String url;

    /**
     * Счётчик запросов на конвертацию ссылки URL
     */
    private int total;
}
