package ru.job4j.urlshortcut.service.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.service.registration.RegistrationService;
import ru.job4j.urlshortcut.service.site.SiteService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс используется для выполнения модульных тестов
 * сервиса Аутентификации
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class AuthenticationServiceTest {

    /**
     * Доменное имя регистрируемого сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Объект - Зарегистрированный в приложении сайт
     */
    private Site registeredSite;

    /**
     * Внедрение зависимости от сервиса Регистрации
     */
    @Autowired
    private RegistrationService registrationService;

    /**
     * Внедрение зависимости от сервиса Аутентификации
     */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Внедрение зависимости от AuthenticationManager
     */
    @Autowired
    private AuthenticationManager authManager;

    /**
     * Внедрение зависимости от сервиса Сайтов
     */
    @Autowired
    private SiteService siteService;

    /**
     * Инициализация регистрации сайта в приложении до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenRegisterNewSiteInApp() {
        registeredSite = registrationService.register(DOMAIN_NAME);
    }

    /**
     * Очистка всех данных из таблиц после выполнения каждого теста
     */
    @AfterEach
    void wipeTablesAfterTests() {
        siteService.deleteByDomainName(DOMAIN_NAME);
    }

    /**
     * Тест проверяет сценарий успешной аутентификации пользователя в
     * приложении по высланным регистрационным данным
     */
    @Test
    void whenSuccessSignInThenGetAuthenticationAndUsername() {
        Credential credential = new Credential(
                registeredSite.getCredential().getLogin(),
                registeredSite.getCredential().getPassword());
        Authentication rsl = authenticationService.authenticate(authManager, credential);
        assertTrue(rsl.isAuthenticated());
        assertEquals(registeredSite.getCredential().getLogin(), rsl.getName());
    }

    /**
     * Тест проверяет сценарий неуспешной аутентификации пользователя в
     * приложении
     */
    @Test

    void whenUnSuccessSignInThenGetException() {
        Credential credential = new Credential("someLogin", "somePass");
        assertThrows(BadCredentialsException.class,
                () -> authenticationService.authenticate(authManager, credential)
        );
    }
}