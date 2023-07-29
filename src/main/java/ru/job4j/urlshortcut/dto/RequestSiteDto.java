package ru.job4j.urlshortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * DTO регистрация сайта в виде запроса
 */
@Data
public class RequestSiteDto {

    /**
     * Доменное имя сайта
     */
    @NotBlank(message = "Domain name must be non null!")
    @Pattern(regexp = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)+"
                            + "([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$",
            message = "Domain name must be correct!")
    private String site;
}
