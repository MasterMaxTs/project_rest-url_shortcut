package ru.job4j.urlshortcut.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.urlshortcut.UrlShortcutApplication;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.service.authentication.AuthenticationService;
import ru.job4j.urlshortcut.service.jwt.JwtService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.job4j.urlshortcut.util.jwt.JwtConstants.headerString;

/**
 * Класс используется для выполнения модульных тестов
 * контроллера Аутентификации
 */
@SpringBootTest(classes = UrlShortcutApplication.class)
@ActiveProfiles(value = "test")
class AuthenticationControllerTest {

    /**
     * Объект MockMvc
     */
    private MockMvc mockMvc;

    /**
     * Объект-заглушка Сервис Аутентификации
     */
    @MockBean
    private AuthenticationService authenticationService;

    /**
     * Объект-заглушка Authentication Manager
     */
    @MockBean
    private AuthenticationManager authManager;

    /**
     * Внедрение зависимости от сервиса JSON Web Token
     */
    @Autowired
    private JwtService jwtService;

    /**
     * Внедрение зависимости от HttpServletResponse
     */
    @Autowired
    private HttpServletResponse response;

    /**
     * Объект - Учётные данные
     */
    private Credential credential;

    /**
     * Объект - Результат аутентификации
     */
    private Authentication authResult;

    /**
     * Инициализация объектов до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenInitObjects() {
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(
                        new AuthenticationController(
                                authenticationService, authManager, jwtService)
                ).build();
        credential = new Credential("login", "pass");
        authResult = new UsernamePasswordAuthenticationToken(
                            new User(
                                    credential.getLogin(),
                                    credential.getPassword(),
                                    List.of()),
                    null);
    }

    /**
     * Тест проверяет сценарий успешной аутентификации пользователя в
     * приложении, основанный на проверке статуса и наличия заголовка в ответе
     * от сервера
     */
    @Test
    void whenSuccessSignInThenStatusIs200AndResponseHeaderExists() throws Exception {
        doReturn(authResult)
                .when(authenticationService).authenticate(authManager, credential);
        jwtService.createToken(authResult, response);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"" + credential.getLogin() + "\","
                                + "\"password\":\"" + credential.getPassword() + "\"}")
                ).andDo(print()
                ).andExpect(status().isOk()
                ).andExpect(header().exists(headerString));
    }

    /**
     * Тест проверяет сценарий не успешной аутентификации пользователя в
     * приложении, основанный на проверке статуса и наличия json-контента в
     * ответе от сервера
     */
    @Test
    void whenFailSignInThenStatusIs404AndJsonContentForError() throws Exception {
        doThrow(BadCredentialsException.class)
                .when(authenticationService).authenticate(authManager, credential);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"" + credential.getLogin() + "\","
                                + "\"password\":\"" + credential.getPassword() + "\"}")
                ).andDo(print()
                ).andExpect(status().isNotFound()
                ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}