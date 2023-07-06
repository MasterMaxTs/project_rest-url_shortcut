package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.urlshortcut.domain.Site;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище зарегистрированных сайтов
 */
@Repository
public interface SiteCrudRepository extends CrudRepository<Site, Integer> {

    /**
     * Выполняет поиск сайта в хранилище по URL
     * @param url URL сайта
     * @return результат поиска в виде Optional
     */
    @Query("from Site where url = :url")
    Optional<Site> findByUrl(@Param("url") String url);

    /**
     * Получает список всех зарегистрированных сайтов из хранилища
     * @return список сайтов
     */
    @Query("from Site")
    List<Site> findAll();


}
