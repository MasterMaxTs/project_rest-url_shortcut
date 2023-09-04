package ru.job4j.urlshortcut.service.site;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.service.credential.CredentialService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс используется для выполнения модульных тестов сервиса Сайтов
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class SiteServiceTest {

    /**
     * Доменное имя регистрируемого сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Внедрение зависимости от сервиса Сайтов
     */
    @Autowired
    private SiteService siteService;

    /**
     * Внедрение зависимости от сервиса Учётных данных
     */
    @Autowired
    private CredentialService credentialService;

    /**
     * Модель Сайт
     */
    private Site site;

    /**
     * Модель Учётные данные
     */
    private Credential credential;

    /**
     * Инициализация начальных данных в виде сохранения сайта в БД
     * до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenSaveNewSiteWithCredentialsIntoDb() {
        saveSiteIntoDb();
    }

    /**
     * Сохраняет сайт с учётными данными для входа в БД
     */
    private void saveSiteIntoDb() {
        site = new Site();
        site.setSite(DOMAIN_NAME);
        credential = credentialService.save(
                credentialService.generateCredentials()
        );
        site.setCredential(credential);
        siteService.save(site);
    }

    /**
     * Очистка всех данных из связанных таблиц после выполнения каждого теста
     */
    @AfterEach
    void wipeAllTables() {
        siteService.deleteByDomainName(DOMAIN_NAME);
    }

    /**
     * Тест проверяет сценарий извлечения существующего сайта из хранилища
     * по его доменному имени
     */
    @Test
    void whenFindSiteByDomainNameThenGetFormDb() {
        Site siteInDb =
                siteService.findSiteByDomainName(site.getSite());
        assertEquals(DOMAIN_NAME, siteInDb.getSite());
        assertEquals(credential.getLogin(),
                siteInDb.getCredential().getLogin()
        );
        assertEquals(credential.getPassword(),
                siteInDb.getCredential().getPassword()
        );
    }

    /**
     * Тест проверяет сценарий извлечения не существующего сайта из хранилища
     * по его доменному имени
     */
    @Test
    void whenFindSiteByDomainNameThenGetException() {
        assertThrows(NoSuchElementException.class,
                () -> siteService.findSiteByDomainName("unknown.site"));
    }

    /**
     * Тест проверяет сценарий корректного удаления сайта из хранилища
     * по его доменному имени
     */
    @Test
    void whenDeleteSiteByDomainNameThenGetEmpty() {
        siteService.deleteByDomainName(DOMAIN_NAME);
        assertThrows(NoSuchElementException.class,
                () -> siteService.findSiteByDomainName(DOMAIN_NAME));
        assertFalse(credentialService.findByLogin(site.getCredential().getLogin()));
        saveSiteIntoDb();
    }
}