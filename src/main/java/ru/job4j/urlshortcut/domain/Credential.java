package ru.job4j.urlshortcut.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель данных Учётные данные
 */
@Data
@Entity
@Table(name = "credentials")
public class Credential {

    /**
     * Идентифиактор учётных данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Уникальный логин
     */
    private String login;

    /**
     * Уникальный пароль
     */
    private String password;
}
