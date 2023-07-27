package ru.job4j.urlshortcut.service.statistic;

import ru.job4j.urlshortcut.domain.Statistic;

import java.util.List;

/**
 * Сервис статистических данных сконвертированных URL
 */
public interface StatisticService {

    /**
     * Сохраняет объект статистики в БД
     * @param statistic объект Statistic на входе
     * @return объект Statistic с проинициализированным id
     */
    Statistic save(Statistic statistic);

    /**
     * Предоставляет статистические данные в виде списка
     * @param domainName доменное имя сайта на входе
     * @return список статистических данных
     */
    List<Statistic> getStatistic(String domainName);

    /**
     * Увеличивает счётчик при конвертировании ссылки на единицу в БД
     * @param urlId идентификатор ссылки
     */
    void increaseCounter(int urlId);
}
