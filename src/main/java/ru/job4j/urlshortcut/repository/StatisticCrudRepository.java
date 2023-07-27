package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.urlshortcut.domain.Statistic;

import java.util.List;

/**
 * Хранилище статистических данных сконвертированных URL
 */
public interface StatisticCrudRepository
                            extends CrudRepository<Statistic, Integer> {

    /**
     * Предоставляет статистические данные в виде списка
     * @param domainName доменное имя сайта на входе
     * @return список статистических данных
     */
    @Query("from Statistic s where s.url.site.site = :domainName")
    List<Statistic> getStatistic(@Param("domainName") String domainName);

    /**
     * Увеличивает счётчик при конвертировании ссылки на единицу в БД
     * @param urlId идентификатор ссылки
     */
    @Modifying
    @Query(value = "update site_urls_statistics set total = "
            + "(select total from site_urls_statistics where url_id = :urlId)"
            + " + 1 "
            + "where url_id = :urlId",
           nativeQuery = true)
    void increaseCounter(@Param("urlId") int urlId);
}
