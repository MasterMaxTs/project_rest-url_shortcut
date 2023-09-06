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
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.handler.GlobalExceptionHandler;
import ru.job4j.urlshortcut.service.site.SiteService;
import ru.job4j.urlshortcut.service.statistic.StatisticService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс используется для выполнения модульных тестов
 * контроллера Статистики
 */
@WebMvcTest(controllers = StatisticController.class)
@ActiveProfiles(value = "test")
class StatisticControllerTest {

    /**
     * Доменное имя сайта
     */
    private static final String DOMAIN_NAME = "test.com";

    /**
     * Имя URL сайта
     */
    private static final String URL_NAME = "http://" + DOMAIN_NAME + "/t/1";

    /**
     * Объект MockMvc
     */
    private MockMvc mockMvc;

    /**
     * Объект-заглушка Сервис статистики
     */
    @MockBean
    private StatisticService statisticService;

    /**
     * Объект-заглушка Сервис сайтов
     */
    @MockBean
    private SiteService siteService;

    /**
     * Список статистических данных сайта
     */
    private List<Statistic> statistics;

    /**
     * Инициализация объектов до выполнения каждого теста
     */
    @BeforeEach
    void whenSetUpThenInitObjects() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                            new StatisticController(statisticService)
                        ).setControllerAdvice(new GlobalExceptionHandler()
                        ).build();
        Statistic firstValue = new Statistic(new Url(DOMAIN_NAME));
        Statistic secondValue = new Statistic(new Url(URL_NAME));
        secondValue.setTotal(2);
        statistics = List.of(firstValue, secondValue);
    }

    /**
     * Тест проверяет сценарий получения существующих статистических данных,
     * основанный на проверке статуса и json-контента в ответе от сервера
     */
    @Test
    void whenGetStatisticsAndSiteExistsInAppThenGetStatusIs200AndJsonContent() throws Exception {
        doReturn(new Site()).when(siteService).findSiteByDomainName(DOMAIN_NAME);
        doReturn(statistics).when(statisticService).getStatistic(DOMAIN_NAME);
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/statistic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"site\":\"" + DOMAIN_NAME + "\"}")
        ).andDo(print()
        ).andExpect(status().isOk()
        ).andExpect(content().json(
                "[{\"url\":\"test.com\",\"total\":0},"
                + "{\"url\":\"" + URL_NAME + "\",\"total\":2}]"));
    }

    /**
     * Тест проверяет сценарий, когда запрошены статистические данные
     * для не зарегистрированного в приложении сайта,
     * основанный на проверке статуса и json-контента в ответе от сервера
     */
    @Test
    void whenGetStatisticsAndSiteDoesNotExistsInAppThenStatusIs404AndJsonContentForError() throws Exception {
        doThrow(NoSuchElementException.class)
                .when(statisticService).getStatistic(DOMAIN_NAME);
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/statistic")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"site\":\"" + DOMAIN_NAME + "\"}")
        ).andDo(print()
        ).andExpect(status().isNotFound()
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}