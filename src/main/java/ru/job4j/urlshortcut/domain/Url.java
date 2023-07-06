package ru.job4j.urlshortcut.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Модель данных Уникальный код для URL сайта
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "url_codes")
public class Url {

    /**
     * Идентификатор кода
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Строковое значение уникального кода для URL сайта
     */
    @EqualsAndHashCode.Include
    private String code;
}
