package ru.job4j.urlshortcut.service.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.repository.StatisticCrudRepository;
import ru.job4j.urlshortcut.service.site.SiteService;

import java.util.List;

/**
 * Реализация сервиса статистических данных сконвертированных URL
 */
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    /**
     * Зависимость от хранилища Статистических данных сконвертированных URL
     */
    private final StatisticCrudRepository store;

    /**
     * Зависимость от сервиса Сайтов
     */
    private final SiteService siteService;

    @Transactional
    @Override
    public Statistic save(Statistic statistic) {
        return store.save(statistic);
    }

    @Transactional
    @Override
    public List<Statistic> getStatistic(String domainName) {
        Site siteInDb = siteService.findSiteByDomainName(domainName);
        List<Statistic> statistics = store.getStatistic(siteInDb.getSite());
        statistics.add(0, new Statistic(new Url(domainName)));
        return statistics;
    }

    @Transactional
    @Override
    public void increaseCounter(int urlId) {
        store.increaseCounter(urlId);
    }

    @Transactional
    @Override
    public void deleteAll() {
        store.deleteAll();
    }
}
