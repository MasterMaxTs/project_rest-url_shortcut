package ru.job4j.urlshortcut.service.url;

import ru.job4j.urlshortcut.domain.Url;

import java.util.Optional;

/**
 * Сервис Ссылок URL
 */
public interface UrlService {

    /**
     * Сохраняет модель в хранилище
     * @param url URL сайта
     * @return модель Уникальный код для URL сайта с проинициализированным id
     */
    Url save(Url url);

    /**
     * Конвертирует URL в кодовое представление
     * @param requestUrl строковое значение URL на входе
     * @return строковое значение кода для URL
     */
    String convert(String requestUrl);

    /**
     * Выполняет поиск URL по имени
     * @param urlName имя URL сайта на входе
     * @return результат поиска в виде Optional
     */
    Optional<Url> findUrlByUrlName(String urlName);

    /**
     * Выполняет поиск URL сайта в хранилище по его коду
     * @param code код URL сайта на входе
     * @return результат поиска с возможным выбросом исключения,
     * если искомый URL сайта не найден
     */
    Url findUrlByCode(String code);

    /**
     * Проверяет наличие Url кода в хранилище
     * @param urlCode проверяемый Url код на входе
     * @return результат проверки в виде boolean
     */
    boolean existsByCode(String urlCode);
}
