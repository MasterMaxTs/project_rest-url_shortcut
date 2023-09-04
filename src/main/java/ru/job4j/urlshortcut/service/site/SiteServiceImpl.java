package ru.job4j.urlshortcut.service.site;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.repository.SiteCrudRepository;

import java.util.NoSuchElementException;
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

    @Transactional
    @Override
    public Site findSiteByDomainName(String domainName) {
        Optional<Site> optionalSite =
                siteStore.findSiteByDomainName(domainName);
        if (optionalSite.isEmpty()) {
            throw new NoSuchElementException(
                    String.format(
                            "The site with the domain name '%s'"
                                    + " was not found in the database."
                                    + " You need to register the site domain name"
                                    + " in the application!!",
                            domainName
                    )
            );
        }
        return optionalSite.get();
    }

    @Transactional
    @Override
    public Site save(Site site) {
        return siteStore.save(site);
    }

    @Transactional
    @Override
    public void deleteByDomainName(String domainName) {
        Site siteInDb = findSiteByDomainName(domainName);
        siteStore.delete(siteInDb);
    }
}
