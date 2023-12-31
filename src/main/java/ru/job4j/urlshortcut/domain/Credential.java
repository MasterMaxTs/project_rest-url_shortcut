package ru.job4j.urlshortcut.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Модель данных - Учётные данные при регистрации сайта в приложении
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class Credential {

    /**
     * Идентификатор учётных данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Уникальный логин
     */
    @NotBlank(message = "login must be non null!")
    private String login;

    /**
     * Уникальный пароль
     */
    @NotBlank(message = "password must be non null!")
    private String password;

    /**
     * Конструктор
     * @param login логин
     * @param password пароль
     */
    public Credential(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
