package ru.job4j.urlshortcut.service.site;

import ru.job4j.urlshortcut.domain.Site;

/**
 * Сервис сайтов
 */
public interface SiteService {

    /**
     * Выполняет поиск сайта в хранилище по доменному имени
     * @param domainName доменное имя сайта
     * @return результат поиска с возможным выбросом исключения,
     * если искомый сайт не найден
     */
    Site findSiteByDomainName(String domainName);

    /**
     * Сохраняет модель в хранилище
     * @param site модель данных Сайт
     * @return модель Сайт с проинициализированным id
     */
    Site save(Site site);

    /**
     * Удаляет сайт из хранилища по его доменному имени
     * @param domainName доменное имя
     */
    void deleteByDomainName(String domainName);
}
