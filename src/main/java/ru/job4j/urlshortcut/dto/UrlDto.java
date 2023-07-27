package ru.job4j.urlshortcut.dto;

import lombok.Data;

/**
 * DTO ссылка URL в виде запроса
 */
@Data
public class UrlDto {

    /**
     * Значение ссылки URL
     */
    private String url;
}
