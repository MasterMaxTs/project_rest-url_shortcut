package ru.job4j.urlshortcut.util.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс используется для выполнения модульных тестов утилитарного
 * класса Парсер ссылок URL протоколов Http, Https
 */
class HttpUrlParserTest {

    /**
     * Тест проверяет сценарий парсинга доменного имени сайта из ссылок URL
     * протоколов Http, Https
     */
    @Test
    void whenParseDomainNameFromHttpAndHttpsUrlThenGet() {
        String expectedDomain = "test.ru";
        String requestHttpUrl = "http://test.ru/exercise/1";
        String requestHttpsUrl = "https://test.ru/exercise/1";
        assertEquals(expectedDomain,
                HttpUrlParser.parseDomainNameFromUrl(requestHttpUrl));
        assertEquals(expectedDomain,
                HttpUrlParser.parseDomainNameFromUrl(requestHttpsUrl));
    }
}