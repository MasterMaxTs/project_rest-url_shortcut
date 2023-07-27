package ru.job4j.urlshortcut.service.site;

import ru.job4j.urlshortcut.domain.Site;

import java.util.Optional;

/**
 * Сервис сайтов
 */
public interface SiteService {

    /**
     * Выполняет поиск сайта в хранилище по доменному имени
     * @param domain доменное имя сайта
     * @return результат поиска в виде Optional
     */
    Optional<Site> findSiteByDomainName(String domain);

    /**
     * Сохраняет модель в хранилище
     * @param site модель данных Сайт
     * @return модель Сайт с проинициализированным id
     */
    Site save(Site site);
}
