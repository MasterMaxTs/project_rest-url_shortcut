package ru.job4j.urlshortcut.service.statistic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.service.credential.CredentialService;
import ru.job4j.urlshortcut.service.site.SiteService;
import ru.job4j.urlshortcut.service.url.UrlService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Класс используется для выполнения модульных тестов сервиса
 * Статистических данных
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class StatisticServiceTest {

    /**
     * Доменное имя первого сайта
     */
    private static final String SITE_1_DOMAIN_NAME = "test1.com";

    /**
     * Доменное имя второго сайта
     */
    private static final String SITE_2_DOMAIN_NAME = "test2.com";

    /**
     * Первое имя URL для первого сайта
     */
    private static final String SITE_1_URL_NAME_1 =
                                 "https://" + SITE_1_DOMAIN_NAME + "/1";

    /**
     * Второе имя URL для первого сайта
     */
    private static final String SITE_1_URL_NAME_2 =
                                "https://" + SITE_1_DOMAIN_NAME + "/2";

    /**
     * Первое имя URL для второго сайта
     */
    private static final String SITE_2_URL_NAME_1 =
                                 "https://" + SITE_2_DOMAIN_NAME + "/3";

    /**
     * Внедрение зависимости от Сервиса статистических данных
     */
    @Autowired
    private StatisticService statisticService;

    /**
     * Внедрение зависимости от Сервиса сайтов
     */
    @Autowired
    private SiteService siteService;

    /**
     * Внедрение зависимости от Сервиса учётных данных
     */
    @Autowired
    private CredentialService credentialService;

    /**
     * Внедрение зависимости от Сервиса ссылок URL
     */
    @Autowired
    private UrlService urlService;

    /**
     * Объект первый сайт
     */
    private Site site1;

    /**
     * Инициализация объектов, сохранение двух сайтов в БД до выполнения
     * каждого теста
     */
    @BeforeEach
    void whenSetUpSaveSitesIntoDb() {
        Credential credential1 =
                credentialService.save(credentialService.generateCredentials());
        Credential credential2 =
                credentialService.save(credentialService.generateCredentials());
        site1 = new Site(SITE_1_DOMAIN_NAME, false, credential1);
        Site site2 = new Site(SITE_2_DOMAIN_NAME, false, credential2);
        siteService.save(site1);
        siteService.save(site2);
    }

    /**
     * Очистка данных из таблиц в БД после выполнения каждого теста
     */
    @AfterEach
    void wipeAllTables() {
        statisticService.deleteAll();
        siteService.deleteByDomainName(SITE_1_DOMAIN_NAME);
        siteService.deleteByDomainName(SITE_2_DOMAIN_NAME);
    }

    /**
     * Тест проверяет сценарий получения статистических данных после конвертации
     * ссылок URL для зарегистрированных в приложении сайтов
     */
    @Test
    void whenConvertUrlAndSiteExistsInAppThenGetStatistic() {
        urlService.convert(SITE_1_URL_NAME_1);
        urlService.convert(SITE_1_URL_NAME_2);
        urlService.convert(SITE_1_URL_NAME_2);
        urlService.convert(SITE_2_URL_NAME_1);
        List<Statistic> statistics =
                statisticService.getStatistic(SITE_1_DOMAIN_NAME);
        assertEquals(3, statistics.size());
        assertEquals(SITE_1_DOMAIN_NAME, statistics.get(0).getUrl().getUrl());
        assertEquals(0, statistics.get(0).getTotal());
        assertEquals(SITE_1_URL_NAME_2, statistics.get(2).getUrl().getUrl());
        assertEquals(2, statistics.get(2).getTotal());
    }

    /**
     * Тест проверяет сценарий, когда осуществляется попытка получить
     * статистические данные для не зарегистрированного в приложении сайта
     */
    @Test
    void whenTryingToGetStatisticAndSiteDoesNotExistsThenException() {
        assertThrows(NoSuchElementException.class,
                () -> statisticService.getStatistic(
                        SITE_1_DOMAIN_NAME.replace('1', '3'))
        );
    }

    /**
     * Тест проверяет сценарий корректности увеличения счётчика
     * сконвертированных ссылок URL
     */
    @Test
    void whenIncreaseCounterThenGet() {
        Url urlSite1 = new Url(SITE_1_URL_NAME_1, site1);
        Url urlInDb = urlService.save(urlSite1);
        statisticService.save(new Statistic(urlInDb));
        statisticService.increaseCounter(urlInDb.getId());
        statisticService.increaseCounter(urlInDb.getId());
        int rsl =
                statisticService.getStatistic(SITE_1_DOMAIN_NAME).get(1).getTotal();
        assertEquals(2, rsl);
    }

    /**
     * Тест проверяет сценарий корректности очистки всех статистических данных
     */
    @Test
    void deleteAll() {
        statisticService.deleteAll();
        assertEquals(1, statisticService.getStatistic(SITE_1_DOMAIN_NAME).size());
        assertEquals(1, statisticService.getStatistic(SITE_2_DOMAIN_NAME).size());
    }
}