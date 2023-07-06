package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Url;

import java.util.Optional;

/**
 * Хранилище уникальных кодов для URL сайтов
 */
public interface UrlCrudRepository extends CrudRepository<Url, Integer> {

    /**
     * Выполняет поиск URL сайта в хранилище по коду
     * @param code код URL
     * @return результат поиска в виде Optional
     */
    Optional<Url> findByCode(String code);
}
