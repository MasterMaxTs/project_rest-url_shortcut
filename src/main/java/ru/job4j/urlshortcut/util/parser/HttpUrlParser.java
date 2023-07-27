package ru.job4j.urlshortcut.util.parser;

/**
 * Класс - Http, Https URL-парсер
 */
public class HttpUrlParser {

    /**
     * Парсит из ссылки URL доменное имя сайта
     * @param url ссылка URL на входе
     * @return распарсенное доменное имя сайта
     */
    public static String parseDomainNameFromUrl(String url) {
        String prefix = url.startsWith("http:")
                ? url.substring(0, 8)
                : url.substring(0, 9);
        String path = url.substring(prefix.length() - 1);
        return path.split("/")[0];
    }
}
