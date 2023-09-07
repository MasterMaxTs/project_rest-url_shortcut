package ru.job4j.urlshortcut.service.credential;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс используется для выполнения модульных тестов сервиса Учётных данных
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class CredentialServiceTest {

    /**
     * Внедрение зависимости от сервиса Учётных данных
     */
    @Autowired
    private CredentialService credentialService;

    /**
     * Модель - Учётные данные
     */
    private Credential credential;

    /**
     * Инициализация рандомной генерации учётных данных до выполнения каждого
     * теста
     */
    @BeforeEach
    void whenSetUpThenGenerateCredentials() {
        credential = credentialService.generateCredentials();
    }

    /**
     * Тест проверяет сценарий генерации учётных данных
     */
    @Test
    void whenGenerateCredentialsThenGet() {
        assertTrue(credential.getLogin().length() > 0);
        assertTrue(credential.getPassword().length() > 0);
    }

    /**
     * Тест проверяет сценарий успешного нахождения учётных данных в хранилище
     * по логину
     */
    @Test
    void whenFindCredentialsByLoginThenTrue() {
        credentialService.save(credential);
        assertTrue(credentialService.findByLogin(credential.getLogin()));
    }

    /**
     * Тест проверяет сценарий неуспешного нахождения учётных данных в хранилище
     * по логину
     */
    @Test
    void whenFindCredentialsByLoginThenFalse() {
        credentialService.save(credential);
        assertFalse(credentialService.findByLogin("someLogin"));
    }

    /**
     * Тест проверяет сценарий сохранения сгенерированных учётных данных в
     * хранилище
     */
    @Test
    void whenSaveCredentialsThenGetFromDb() {
        Credential rsl = credentialService.save(credential);
        assertTrue(rsl.getId() > 0);
        assertTrue(credentialService.findByLogin(rsl.getLogin()));
    }

    /**
     * Тест проверяет сценарий успешного нахождения учётных данных в хранилище
     * по паролю
     */
    @Test
    void whenFindCredentialsByPasswordThenTrue() {
        credentialService.save(credential);
        assertTrue(credentialService.findByPassword(credential.getPassword()));
    }

    /**
     * Тест проверяет сценарий неуспешного нахождения учётных данных в хранилище
     * по паролю
     */
    @Test
    void whenFindCredentialsByPasswordThenFalse() {
        credentialService.save(credential);
        assertFalse(credentialService.findByPassword("somePass"));
    }
}