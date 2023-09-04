package ru.job4j.urlshortcut.service.registration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.service.credential.CredentialService;
import ru.job4j.urlshortcut.service.site.SiteService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс используется для выполнения модульных тестов сервиса Регистрации
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class RegistrationServiceTest {

    /**
     * Доменное имя регистрируемого сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Внедрение зависимости от сервиса Регистрации
     */
    @Autowired
    private RegistrationService registrationService;

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
     * Очистка всех данных из таблиц после выполнения каждого теста
     */
    @AfterEach
    void wipeTablesAfterTests() {
        siteService.deleteByDomainName(DOMAIN_NAME);
    }

    /**
     * Тест проверяет сценарий успешной регистрации сайта в приложении
     */
    @Test
    void whenSuccessRegisterInAppThenGetSiteFromDb() {
        Site registeredSite = registrationService.register(DOMAIN_NAME);
        assertTrue(registeredSite.isRegistration());
        assertNotNull(registeredSite.getCredential());
    }

    /**
     * Тест проверяет сценарий неуспешной регистрации сайта в приложении:
     * повторная регистрация
     */
    @Test
    void whenUnSuccessRegisterInAppThenGetException() {
        registrationService.register(DOMAIN_NAME);
        assertThrows(DataIntegrityViolationException.class,
                () -> registrationService.register(DOMAIN_NAME));
    }
}