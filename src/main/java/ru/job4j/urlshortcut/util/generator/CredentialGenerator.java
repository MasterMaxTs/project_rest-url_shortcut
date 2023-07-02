package ru.job4j.urlshortcut.util.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * Утилитарный класс - генератор коллекции учётных данных
 */
public class CredentialGenerator {

    /**
     * Константа - требуемое количество сгенерированных учетных данных
     */
    static final int REQUIRED_COUNT = 100;

    /**
     * Константа - требуемая длина логина
     */
    private static final int REQUIRED_LOGIN_LENGTH = 6;

    /**
     * Константа - требуемая длина пароля
     */
    private static final int REQUIRED_PASSWORD_LENGTH = 8;

    /**
     * Константа - начальный символ в виде Unicode Number для генерации логина
     */
    private static final int START_UNICODE_NUMBER_FOR_LOGIN = 65;

    /**
     * Константа - конечный символ в виде Unicode Number для генерации логина
     */
    private static final int END_UNICODE_NUMBER_FOR_LOGIN = 122;

    /**
     * Константа - начальный символ в виде Unicode Number для генерации пароля
     */
    private static final int START_UNICODE_NUMBER_FOR_PASSWORD = 48;

    /**
     * Константа - конечный символ в виде Unicode Number для генерации пароля
     */
    private static final int END_UNICODE_NUMBER_FOR_PASSWORD = 122;

    /**
     * Метод генирации учётных данных
     * @return карту уникальных учётных данных, где в качестве ключа
     * используется логин, а в качестве значения - пароль
     */
    public static Map<String, String> generateCredentials() {
        Map<String, String> credentials = new HashMap<>();
        while (credentials.size() != REQUIRED_COUNT) {
            credentials.putIfAbsent(
                    generate(START_UNICODE_NUMBER_FOR_LOGIN,
                            END_UNICODE_NUMBER_FOR_LOGIN,
                            REQUIRED_LOGIN_LENGTH),
                    generate(START_UNICODE_NUMBER_FOR_PASSWORD,
                            END_UNICODE_NUMBER_FOR_PASSWORD,
                            REQUIRED_PASSWORD_LENGTH));
        }
        return credentials;
    }

    /**
     * Метод генерирует случайную последовательность символов из
     * заданного диапазона символов в виде Unicode Number
     * @param fromUnicodeNumber начальный символ в виде Unicode Number для генерации
     * @param toUnicodeNumber конечный символ в виде Unicode Number для генерации
     * @param requiredLength требуемая длина генерируемой последовательности
     * символов
     * @return сгенерированную случайную последовательность символов
     */
    public static String generate(int fromUnicodeNumber, int toUnicodeNumber,
                                   int requiredLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < requiredLength; i++) {
            int unicodeNumber =
                    (int) (fromUnicodeNumber + Math.round(
                            (toUnicodeNumber - fromUnicodeNumber) * Math.random()));
            sb.append((char) unicodeNumber);
        }
        return sb.toString();
    }
}
