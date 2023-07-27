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
     * Выполняет поиск сайта в хранилище по доменному имени
     * @param site доменное имя сайта
     * @return результат поиска в виде Optional
     */
    @Query("from Site where site = :site")
    Optional<Site> findSiteByDomainName(@Param("site") String site);

    /**
     * Получает список всех зарегистрированных сайтов из хранилища
     * @return список сайтов
     */
    @Query("from Site")
    List<Site> findAll();


}
