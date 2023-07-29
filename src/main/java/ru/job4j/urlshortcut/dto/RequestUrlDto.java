package ru.job4j.urlshortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * DTO ссылка URL в виде запроса
 */
@Data
public class RequestUrlDto {

    /**
     * Значение ссылки URL
     */
    @NotBlank(message = "URL value cannot be null!")
    @Pattern(regexp = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\."
                            + "[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$",
            message = "URL must be correct! URL must start with 'http' or 'https'")
    private String url;
}
