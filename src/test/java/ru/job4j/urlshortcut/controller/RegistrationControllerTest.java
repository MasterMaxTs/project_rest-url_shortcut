package ru.job4j.urlshortcut.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.service.registration.RegistrationService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс используется для выполнения модульных тестов
 * контроллера Регистрации
 */
@WebMvcTest(controllers = RegistrationController.class)
@ActiveProfiles(value = "test")
class RegistrationControllerTest {

    /**
     * Доменное имя регистрируемого сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Объект MockMvc
     */
    private MockMvc mockMvc;

    /**
     * Объект-заглушка Сервис регистрации
     */
    @MockBean
    private RegistrationService registrationService;

    /**
     * Инициализация объекта MockMvc до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenInitMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                    new RegistrationController(registrationService)
                    ).build();
    }

    /**
     * Тест проверяет сценарий успешной регистрации пользователя в
     * приложении, основанный на проверке статуса и json-контента в
     * ответе от сервера
     */
    @Test
    void whenSuccessSignUpThenStatusIs201AndVerifyJsonContent() throws Exception {
        Credential credential = new Credential("login", "pass");
        Site site = new Site(DOMAIN_NAME, true, credential);
        doReturn(site)
                .when(registrationService).register(DOMAIN_NAME);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"site\":\"" + DOMAIN_NAME + "\"}")
        ).andDo(print()
        ).andExpect(status().isCreated()
        ).andExpect(content().json(
                "{\"registration\":" + site.isRegistration() + ","
                        + "\"login\":\"" + site.getCredential().getLogin() + "\","
                + "\"password\":\"" + site.getCredential().getPassword() + "\"}"));
    }

    /**
     * Тест проверяет сценарий не успешной регистрации пользователя в
     * приложении, основанный на проверке статуса и json-контента в
     * ответе от сервера
     */
    @Test
    void whenFailSignUpThenStatusIs400AndVerifyJsonContent() throws Exception {
        String errorMessage = "The site with this domain name is already "
                + "registered in the application.";
        doThrow(ConstraintViolationException.class)
                .when(registrationService).register(DOMAIN_NAME);
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"site\":\"" + DOMAIN_NAME + "\"}")
        ).andDo(print()
        ).andExpect(status().isBadRequest()
        ).andExpect(content().json(
                "{\"registration\":false,"
                        + "\"login\":null,"
                        + "\"password\":null,"
                        + "\"message\":" + "\"" + errorMessage + "\"}"));
    }
}