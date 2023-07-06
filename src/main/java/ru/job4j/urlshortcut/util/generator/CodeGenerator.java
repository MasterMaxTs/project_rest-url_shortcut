package ru.job4j.urlshortcut.util.generator;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * Абстракный класс - генератор кодов
 */
@AllArgsConstructor
public abstract class CodeGenerator {

    /**
     * Требуемая длина кодовой последовательности символов
     */
    private int requiredCodeLength;

    /**
     * Начальный символ (нижний диапазон) в значении юникод для генерации
     * кодовой последовательности
     */
    private int startUnicodeNumber;

    /**
     * Конечный символ (верхний диапазон) в значении юникод для генерации
     * кодовой последовательности
     */
    private int endUnicodeNumber;

    /**
     * Массив символов в значении юникод (из диапазона), неучаствующих в
     * генерации кодовой последовательности
     */
    private int[] excludedUnicodeNumbers;

    /**
     * Рандомно генерирует строковую последовательность символов
     * @return строковую последовательность символов в виде кода
     */
    public String generate() {
        StringBuilder sb = new StringBuilder();
        while (sb.length() != requiredCodeLength) {
            int unicodeNumber =
                    (int) (startUnicodeNumber + Math.round(
                            (endUnicodeNumber - startUnicodeNumber) * Math.random()));
            if (!checkOnExcludedSymbol(unicodeNumber, excludedUnicodeNumbers)) {
                sb.append((char) unicodeNumber);
            }
        }
        return sb.toString();
    }

    /**
     * Выполняет проверку, попадает ли текущий символ из заданного диапазона
     * символов для генерируемой последовательности в список запрещённых
     * символов
     * @param unicodeNumber проверяемый текущий символ в значении юникод
     * @param excludedUnicodeNumbers список запрещённых символов (из диапазона)
     * при генерации кодовой последовательности
     * @return результат проверки в виде boolean
     */
    private boolean checkOnExcludedSymbol(int unicodeNumber,
                                          int[] excludedUnicodeNumbers) {
        return Arrays.stream(excludedUnicodeNumbers)
                .filter(num -> num == unicodeNumber)
                .findAny()
                .isPresent();
    }
}
