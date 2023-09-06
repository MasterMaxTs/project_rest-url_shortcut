package ru.job4j.urlshortcut.util.parser;

/**
 * Утилитарный класс Парсер ссылок URL протоколов Http, Https
 */
public class HttpUrlParser {

    /**
     * Парсит из ссылки URL доменное имя сайта
     * @param url ссылка URL на входе
     * @return распарсенное доменное имя сайта
     */
    public static String parseDomainNameFromUrl(String url) {
        String protocolName = url.split(":")[0];
        String resource = url.substring(protocolName.length() + 3);
        return resource.split("/")[0];
    }
}
