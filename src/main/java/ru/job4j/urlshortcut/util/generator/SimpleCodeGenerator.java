package ru.job4j.urlshortcut.util.generator;

/**
 * Генератор кодовой последовательности
 */
public class SimpleCodeGenerator extends CodeGenerator {

    /**
     * Конструктор
     * @param requiredCodeLength требуемая длина кодовой последовательности
     * символов
     * @param startUnicodeNumber начальный символ (нижний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param endUnicodeNumber конечный символ (верхний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param excludedUnicodeNumbers массив символов в значении юникод
     * (из диапазона), неучаствующих в генерации кодовой последовательности
     */
    public SimpleCodeGenerator(int requiredCodeLength,
                               int startUnicodeNumber,
                               int endUnicodeNumber,
                               int[] excludedUnicodeNumbers) {
        super(requiredCodeLength,
                startUnicodeNumber,
                endUnicodeNumber,
                excludedUnicodeNumbers);
    }

    /**
     * Фабричный метод для экземпляра генератора кода
     * @param requiredCodeLength требуемая длина кодовой последовательности
     * символов
     * @param startUnicodeNumber начальный символ (нижний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param endUnicodeNumber конечный символ (верхний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param excludedUnicodeNumbers массив символов в значении юникод
     * (из диапазона), неучаствующих в генерации кодовой последовательности
     * @return проинициализированный экземпляр генератора кода
     */
    public static SimpleCodeGenerator getCodeGenerator(int requiredCodeLength,
                                                       int startUnicodeNumber,
                                                       int endUnicodeNumber,
                                                       int[] excludedUnicodeNumbers) {
        return new SimpleCodeGenerator(requiredCodeLength,
                                        startUnicodeNumber,
                                        endUnicodeNumber,
                                        excludedUnicodeNumbers);
    }
}
