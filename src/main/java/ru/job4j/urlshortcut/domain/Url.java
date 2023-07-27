package ru.job4j.urlshortcut.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных -  ссылка URL, закреплённая за доменным сайтом
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "site_urls")
public class Url {

    /**
     * Идентификатор ссылки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Строковое значение ссылки
     */
    private String url;

    /**
     * Строковое значение уникального кода для ссылки
     */
    private String code;

    /**
     * Доменный сайт
     */
    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    /**
     * Конструктор
     * @param url строкое значение ссылки
     */
    public Url(String url) {
        this.url = url;
    }

    /**
     * Конструктор
     * @param url строкое значение ссылки
     * @param site объект доменный сайт
     */
    public Url(String url, Site site) {
        this.url = url;
        this.site = site;
    }
}
