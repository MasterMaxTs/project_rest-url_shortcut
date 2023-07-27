package ru.job4j.urlshortcut.dto;

import lombok.Data;

/**
 * DTO регистрация сайта в виде запроса
 */
@Data
public class RegistrationSiteDto {

    /**
     * Доменное имя сайта
     */
    private String site;
}
