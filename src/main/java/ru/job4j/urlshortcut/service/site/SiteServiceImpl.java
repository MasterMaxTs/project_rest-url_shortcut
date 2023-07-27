package ru.job4j.urlshortcut.service.site;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.repository.SiteCrudRepository;

import java.util.Optional;

/**
 * Реализация сервиса сайтов
 */
@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    /**
     * Зависимость от хранилища зарегистрированных сайтов
     */
    private final SiteCrudRepository siteStore;

    @Override
    public Optional<Site> findSiteByDomainName(String domain) {
        return siteStore.findSiteByDomainName(domain);
    }

    @Override
    public Site save(Site site) {
        return siteStore.save(site);
    }
}
