package ru.job4j.urlshortcut.service.url;

import ru.job4j.urlshortcut.domain.Url;

/**
 * Сервис Уникальных кодов для URl сайтов
 */
public interface UrlService {

    /**
     * Сохраняет модель в хранилище
     * @param url модель данных Уникальный код для URL сайта
     * @return модель Уникальный код для URL сайта с проинициализированным id
     */
    Url save(Url url);

    /**
     * Выполняет поиск модели в хранилище по URL коду
     * @param urlCode URL код
     * @return результат поиска в виде boolean
     */
    boolean findByUrlCode(String urlCode);

}
