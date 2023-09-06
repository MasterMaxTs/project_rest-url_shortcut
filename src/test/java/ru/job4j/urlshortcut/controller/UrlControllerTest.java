package ru.job4j.urlshortcut.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.handler.GlobalExceptionHandler;
import ru.job4j.urlshortcut.service.url.UrlService;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Класс используется для выполнения модульных тестов
 * контроллера Ссылок URL
 */
@WebMvcTest(controllers = UrlController.class)
@ActiveProfiles(value = "test")
class UrlControllerTest {

    /**
     * Доменное имя сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Имя URL сайта
     */
    private static final String REQUEST_URL = "http://" + DOMAIN_NAME + "/t/1";

    /**
     * Кодовое представление URL ссылки
     */
    private static final String URL_CODE = "Df1xU0";

    /**
     * Объект MockMvc
     */
    private MockMvc mockMvc;

    /**
     * Объект-заглушка Сервис URL ссылок
     */
    @MockBean
    private UrlService urlService;

    /**
     * Инициализация объекта MockMvc до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenInitMockMvc() {
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(
                        new UrlController(urlService)
                    ).setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    /**
     * Тест проверяет сценарий получения короткой URL ссылки для
     * зарегистрированного в приложении сайта,
     * основанный на проверке статуса и json-контента в ответе от сервера
     */
    @Test
    void whenConvertAndSiteExistsInAppThenGetStatusIs200AndJsonContent() throws Exception {
        doReturn(URL_CODE).when(urlService).convert(REQUEST_URL);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"url\":\"" + REQUEST_URL + "\"}")
        ).andDo(print()
        ).andExpect(status().isOk()
        ).andExpect(content().json(
                "{\"code\":\"" + URL_CODE + "\"}"));
    }

    /**
     * Тест проверяет сценарий получения короткой URL ссылки для
     * не зарегистрированного в приложении сайта,
     * основанный на проверке статуса и json-контента в ответе от сервера
     */
    @Test
    void whenConvertAndSiteDoesNotExistsInAppThenGetStatusIs404AndJsonContent() throws Exception {
        doThrow(NoSuchElementException.class)
                .when(urlService).convert(REQUEST_URL);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"url\":\"" + REQUEST_URL + "\"}")
        ).andDo(print()
        ).andExpect(status().isNotFound()
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    /**
     * Тест проверяет сценарий переадресации запроса по сущестующей в
     * приложении короткой URL ссылке,
     * основанный на проверке статуса и специфических заголовков в ответе от
     * сервера
     */
    @Test
    void whenRedirectAndUrlCodeExistsInAppThenGetStatusIs302AndResponseHeadersExists() throws Exception {
        Url url = new Url(REQUEST_URL);
        doReturn(url).when(urlService).findUrlByCode(URL_CODE);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/redirect/" + URL_CODE)
        ).andDo(print()
        ).andExpect(status().is3xxRedirection()
        ).andExpect(header().string("HTTP CODE", "302")
        ).andExpect(header().string("REDIRECT", url.getUrl()));
    }

    /**
     * Тест проверяет сценарий, когда запрошена переадресация по
     * не сущестующей в приложении короткой URL ссылке,
     * основанный на проверке статуса и json-контента в ответе от
     * сервера
     */
    @Test
    void whenRedirectAndUrlCodeDoesNotExistsInAppThenGetStatusIs404AndJsonContentForError() throws Exception {
        doThrow(NoSuchElementException.class)
                .when(urlService).findUrlByCode(URL_CODE);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/redirect/" + URL_CODE)
        ).andDo(print()
        ).andExpect(status().isNotFound()
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}