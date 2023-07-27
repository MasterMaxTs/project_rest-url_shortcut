package ru.job4j.urlshortcut.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Модель данных - Сайт
 */
@Data
@NoArgsConstructor
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
     * Доменное имя сайта
     */
    private String site;

    /**
     * флаг, показывающий регистрацию сайта в приложении
     */
    private boolean registration;

    /**
     * Регистрационные данные для сайта в приложении
     */
    @OneToOne
    @JoinColumn(name = "credential_id")
    private Credential credential;

    /**
     * Список ссылок, закреплённых за доменным именем сайта
     */
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "site"
    )
    private List<Url> urls;

    /**
     * Контсруктор
     * @param site доменное имя сайта
     * @param registration флаг регистрации сайта в приложении
     * @param credential объект в виде регистрационных данных
     */
    public Site(String site, boolean registration, Credential credential) {
        this.site = site;
        this.registration = registration;
        this.credential = credential;
    }
}
