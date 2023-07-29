package ru.job4j.urlshortcut.service.url;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.repository.UrlCrudRepository;
import ru.job4j.urlshortcut.service.site.SiteService;
import ru.job4j.urlshortcut.service.statistic.StatisticService;
import ru.job4j.urlshortcut.util.generator.CodeGenerator;
import ru.job4j.urlshortcut.util.parser.HttpUrlParser;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Реализация сервиса Ссылок URL
 */
@Service
public class UrlServiceImpl implements UrlService {

    /**
     * Зависимость от хранилища кодов для URL сайтов
     */
    private final UrlCrudRepository store;

    /**
     * Зависимость от сервиса сайтов
     */
    private final SiteService siteService;

    /**
     * Зависимость от сервиса статистических данных сконвертированных URL
     */
    private final StatisticService statisticService;

    /**
     * Зависимость от генератора кода для URL сайта
     */
    private final CodeGenerator codeGenerator;

    /**
     * Конструктор
     * @param store внедрение зависимость от хранилища кодов для URL сайтов
     * @param codeGenerator внедрение зависимости от генератора кода для URL сайта
     */
    public UrlServiceImpl(UrlCrudRepository store,
                          SiteService siteService,
                          StatisticService statisticService,
                          @Qualifier("urlCodeGenerator") CodeGenerator codeGenerator) {
        this.store = store;
        this.siteService = siteService;
        this.statisticService = statisticService;
        this.codeGenerator = codeGenerator;
    }

    @Transactional
    @Override
    public Url save(Url url) {
        String code = codeGenerator.generate();
        while (existsByCode(code)) {
            code = codeGenerator.generate();
        }
        url.setCode(code);
        store.save(url);
        return url;
    }

    @Transactional
    @Override
    public String convert(String requestUrl) {
        String rsl;
        Url urlInDb;
        String domainName = HttpUrlParser.parseDomainNameFromUrl(requestUrl);
        Optional<Site> optionalSite = siteService.findSiteByDomainName(domainName);
        if (optionalSite.isEmpty()) {
            throw new NoSuchElementException(
                    String.format("Domain name is not found! "
                                    + "You need to register the site domain name '%s' "
                                            + "in the application!", domainName));
        }
        Optional<Url> optionalUrl = store.findUrlByUrlName(requestUrl);
        if (optionalUrl.isEmpty()) {
            urlInDb = save(new Url(requestUrl, optionalSite.get()));
            statisticService.save(new Statistic(urlInDb));
            statisticService.increaseCounter(urlInDb.getId());
            rsl = urlInDb.getCode();
        } else {
            urlInDb = optionalUrl.get();
            rsl = urlInDb.getCode();
            statisticService.increaseCounter(urlInDb.getId());
        }
        return rsl;
    }

    @Override
    public Optional<String> findUrlByCode(String code) {
        return store.findUrlByCode(code);
    }

    @Override
    public boolean existsByCode(String urlCode) {
        return store.existsByCode(urlCode);
    }
}
