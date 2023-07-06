package ru.job4j.urlshortcut.service.site;

import ru.job4j.urlshortcut.domain.Site;

/**
 * Сервис зарегистрированных Сайтов
 */
public interface SiteService {

    /**
     * Проверяет регистрацию сайта, исходя из информации в хранилище сайтов
     * @param url URL сайта
     * @return результат проверки в виде boolean
     */
    boolean isRegister(String url);

    /**
     * Сохраняет модель в хранилище
     * @param site модель данных Сайт
     * @return модель Сайт с проинициализированным id
     */
    Site save(Site site);

    boolean update(Site site);

    boolean deleteByUrl(String url);
}
