package ru.job4j.urlshortcut.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель данных Сайт
 */
@Data
@Entity
@Table(name = "sites")
public class Site {

    /**
     * Идентифиактор сайта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * URL сайта
     */
    private String url;

    /**
     * Уникальный код для URL сайта
     */
    @OneToOne
    @JoinColumn(name = "code_id")
    private Code code;

    /**
     * Учётные данные для аутентификации сайта в приложении
     */
    @OneToOne
    @JoinColumn(name = "credential_id")
    private Credential credentials;

    /**
     * флаг, показывающий регистрацию сайта в приложении
     */
    private boolean registration;
}
