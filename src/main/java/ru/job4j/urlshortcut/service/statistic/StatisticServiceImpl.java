package ru.job4j.urlshortcut.service.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.repository.StatisticCrudRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Реализация сервиса статистических данных сконвертированных URL
 */
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    /**
     * Зависимость от StatisticCrudRepository
     */
    private final StatisticCrudRepository store;

    @Transactional
    @Override
    public Statistic save(Statistic statistic) {
        return store.save(statistic);
    }

    @Transactional
    @Override
    public List<Statistic> getStatistic(String domainName) {
        List<Statistic> statistics = store.getStatistic(domainName);
        if (statistics.size() == 0) {
            throw new NoSuchElementException(
                    "The site domain name is not found in the database!"
                            + " Clarify site domain name.");
        }
        statistics.add(0, new Statistic(new Url(domainName)));
        return statistics;
    }

    @Transactional
    @Override
    public void increaseCounter(int urlId) {
        store.increaseCounter(urlId);
    }
}
