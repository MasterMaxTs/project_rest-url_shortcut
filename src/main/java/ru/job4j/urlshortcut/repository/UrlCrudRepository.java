package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.urlshortcut.domain.Url;

import java.util.Optional;

/**
 * Хранилище ссылок URL
 */
public interface UrlCrudRepository extends CrudRepository<Url, Integer> {

    /**
     * Выполняет поиск URL по имени
     * @param urlName имя ссылки URL
     * @return результат поиска в виде Optional
     */
    @Query("from Url where url = :url")
    Optional<Url> findUrlByUrlName(@Param("url") String urlName);

    /**
     * Выполняет поиск URL сайта по его коду в хранилище
     * @param code код URL сайта на входе
     * @return результат поиска в виде Optional
     */
    @Query("from Url where code = :code")
    Optional<Url> findUrlByCode(@Param("code") String code);

    /**
     * Проверяет наличие Url кода в хранилище
     * @param urlCode проверяемый Url код на входе
     * @return результат проверки в виде boolean
     */

    boolean existsByCode(String urlCode);
}