package ru.job4j.urlshortcut.service.url;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.service.credential.CredentialService;
import ru.job4j.urlshortcut.service.site.SiteService;
import ru.job4j.urlshortcut.service.statistic.StatisticService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс используется для выполнения модульных тестов сервиса Ссылок URL
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class UrlServiceTest {

    /**
     * Доменное имя зарегистрированного сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Имя URL зарегистрированного сайта
     */
    private static final String URL_NAME =
                                 "https://" +  DOMAIN_NAME + "/tasks" + "/1";

    /**
     * Внедрение зависимости от сервиса Учётных данных
     */
    @Autowired
    private CredentialService credentialService;

    /**
     * Внедрение зависимости от сервиса Сайтов
     */
    @Autowired
    private SiteService siteService;

    /**
     * Внедрение зависимости от сервиса Ссылок URL
     */
    @Autowired
    private UrlService urlService;

    /**
     * Внедрение зависимости от Сервиса статистических данных
     * сконвертированных URL
     */
    @Autowired
    private StatisticService statisticService;

    /**
     * Модель Ссылка URL
     */
    private Url url;

    /**
     * Инициализация начальных данных в виде сохранения сайта и сохранения
     * ссылки URL в БД до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenSaveNewSiteWithCredentialsAndNewUrlIntoDb() {
        Site site = new Site();
        site.setSite(DOMAIN_NAME);
        Credential credential = credentialService.save(
                credentialService.generateCredentials()
        );
        site.setCredential(credential);
        siteService.save(site);
        url = urlService.save(new Url(URL_NAME, site));
    }

    /**
     * Очистка всех данных из связанных таблиц после выполнения каждого теста
     */
    @AfterEach
    void wipeAllTables() {
        statisticService.deleteAll();
        siteService.deleteByDomainName(DOMAIN_NAME);
    }

    /**
     * Тест проверяет сценарий сохранения ссылки URL в хранилище
     * и извлечения из него по имени URL
     */
    @Test
    void whenSaveUrlThenGetFromDb() {
        Optional<Url> optionalRsl = urlService.findUrlByUrlName(URL_NAME);
        assertTrue(optionalRsl.isPresent());
        Url urlInDb = optionalRsl.get();
        assertEquals(URL_NAME, urlInDb.getUrl());
        assertEquals(DOMAIN_NAME, urlInDb.getSite().getSite());
    }

    /**
     * Тест проверяет сценарий конвертирования URL в кодовое представление
     * и извлечение URL из хранилища по кодовому значению
     */
    @Test
    void whenConvertUrlNameThenGetUrlFromDbWithCode() {
        String rsl = urlService.convert(URL_NAME);
        Url urlInDb = urlService.findUrlByCode(rsl);
        assertTrue(rsl.length() > 0);
        assertEquals(urlInDb.getCode(), rsl);
    }

    /**
     * Тест проверяет сценарий успешного нахождения URL в хранинилище
     * по коду
     */
    @Test
    void whenFindUrlByCodeAndUrlExistsInDbThenGet() {
        Url urlInDb = urlService.findUrlByCode(url.getCode());
        assertEquals(URL_NAME, urlInDb.getUrl());
    }

    /**
     * Тест проверяет сценарий нахождения URL в хранинилище
     * по ошибочному коду
     */
    @Test
    void whenFindUrlByIncorrectCodeThenGetException() {
        assertThrows(NoSuchElementException.class,
                () -> urlService.findUrlByCode("f_8HY>"));
    }

    /**
     * Тест проверяет сценарий добавления нового URL c новым сгенерированным
     * кодом в хранилище и извлечения из него
     */
    @Test
    void whenAddNewUrlThenFindUrlInDbByNewGeneratedCode() {
        String newUrlName = URL_NAME.replace("/1", "/2");
        String newCode = urlService.convert(newUrlName);
        Url urlInDb = urlService.findUrlByCode(newCode);
        assertEquals(newUrlName, urlInDb.getUrl());
    }
}