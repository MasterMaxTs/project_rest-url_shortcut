package ru.job4j.urlshortcut.util.generator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Утилитарный класс - генератор коллекции кодов для URL сайтов
 */
public class CodeGenerator {

    /**
     * Константа - требуемая длина уникального кода для URL
     */
    private static final int REQUIRED_CODE_LENGTH = 6;

    /**
     * Константа - начальный символ в виде Unicode Number для генерации кода
     */
    private static final int START_UNICODE_NUMBER_FOR_CODE = 65;

    /**
     * Константа - конечный символ в виде Unicode Number для генерации кода
     */
    private static final int END_UNICODE_NUMBER_FOR_CODE = 122;

    /**
     * Массив запрещённых символов в виде Unicode Number
     */
    private static final int[] EXCLUDED_UNICODE_NUMBERS = {91, 92, 93, 94, 95, 96};

    /**
     * Метод генирации уникальных кодов для URL сайтов
     * @return коллекцию уникальных кодов для URL сайтов
     */
    public static Set<String> generateCodes() {
        Set<String> codes = new HashSet<>();
        while (codes.size() != CredentialGenerator.REQUIRED_COUNT) {
            codes.add(
                    generate(
                            START_UNICODE_NUMBER_FOR_CODE,
                            END_UNICODE_NUMBER_FOR_CODE,
                            REQUIRED_CODE_LENGTH
                    )
            );
        }
        return codes;
    }

    /**
     * Метод генерирует случайную последовательность символов из
     * заданного диапазона символов, исключая запрещённые, в виде Unicode Number
     * @param fromUnicodeNumber начальный символ в виде Unicode Number для генерации
     * @param toUnicodeNumber конечный символ в виде Unicode Number для генерации
     * @param requiredLength требуемая длина генерируемой последовательности
     * символов
     * @return сгенерированную случайную последовательность символов
     */
    public static String generate(int fromUnicodeNumber, int toUnicodeNumber,
                                  int requiredLength) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() != requiredLength) {
            int unicodeNumber =
                    (int) (fromUnicodeNumber + Math.round(
                            (toUnicodeNumber - fromUnicodeNumber) * Math.random()));
            if (!checkOnExcludedSymbol(unicodeNumber)) {
                sb.append((char) unicodeNumber);
            }
        }
        return sb.toString();
    }

    /**
     * Делает проверку, находится ли сгенерированный символ в списке запрещённых
     * @param unicodeNumber символ в виде Unicode Number на входе
     * @return результат проверки в виде boolean
     */
    public static boolean checkOnExcludedSymbol(int unicodeNumber) {
        return Arrays.stream(EXCLUDED_UNICODE_NUMBERS)
                .filter(num -> num == unicodeNumber)
                .findAny()
                .isPresent();
    }
}
