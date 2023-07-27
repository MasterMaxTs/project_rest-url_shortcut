package ru.job4j.urlshortcut.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель данных - Статистика сконвертированных ссылок, закрепленных за
 * доменным именем
 * сайта
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "site_urls_statistics")
public class Statistic {

    /**
     * Идентификатор статистических данных
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int total;

    /**
     * Ссылка в виде URL
     */
    @OneToOne
    @JoinColumn(name = "url_id")
    private Url url;

    /**
     * Контсруктор
     * @param url URL
     */
    public Statistic(Url url) {
        this.url = url;
    }
}
