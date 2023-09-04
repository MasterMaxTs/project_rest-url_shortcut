package ru.job4j.urlshortcut.service.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.repository.CredentialCrudRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

/**
 * Класс используется для выполнения модульных тестов UserDetailsService
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class UserDetailServiceTest {

    /**
     * Объект-заглушка в виде хранилища учётных данных
     */
    @MockBean
    private CredentialCrudRepository store;

    /**
     * Внедрение зависимости от userDetailService
     */
    @Autowired
    private UserDetailServiceImpl userDetailService;

    /**
     * Тест проверяет сценарий успешной загрузки пользователя в виде UserDetail
     * из хранилища учётных данных
     */
    @Test
    void whenSuccessLoadUserByUsernameThenGetUserDetails() {
        Credential credential = new Credential("login", "password");
        doReturn(Optional.of(credential))
                .when(store).findByLogin(credential.getLogin());
        UserDetails rsl = userDetailService.loadUserByUsername(credential.getLogin());
        assertEquals("login", rsl.getUsername());
        assertEquals("password", rsl.getPassword());
        assertEquals(Collections.emptySet(), rsl.getAuthorities());
    }

    /**
     * Тест проверяет сценарий неуспешной загрузки пользователя из хранилища
     * учётных данных
     */
    @Test
    void whenUnSuccessLoadUserByUsernameThenGetException() {
        String incorrectLogin = "someLogin";
        doReturn(Optional.empty())
                .when(store).findByLogin(incorrectLogin);
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailService.loadUserByUsername(incorrectLogin));
    }
}