package ru.job4j.urlshortcut.util.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Класс используется для выполнения модульных тестов утилитарного
 * класса Генератор кодовой последовательности
 */
class CodeGeneratorTest {

    /**
     * Тест проверяет сценарий проверки серии случайно сгенерированных кодов
     * на предмет их соответствия заданным свойствам: длина кода, диапазон
     * включённых символов, отсутствие в коде запрещённых символов
     */
    @Test
    void whenGenerateRandomCodeThenCheckItForLengthAndForbiddenCharacters() {
        int requiredCodeLength = 4;
        int startUnicodeNumber = 86;
        int endUnicodeNumber = 94;
        int[] excludedUnicodeNumbers = new int[] {91, 92, 93, 94};
        for (int i = 0; i < 100; i++) {
            FactoryCodeGenerator generator =
                                FactoryCodeGenerator.getCodeGenerator(
                                        requiredCodeLength,
                                        startUnicodeNumber,
                                        endUnicodeNumber,
                                        excludedUnicodeNumbers);
            String rsl = generator.generate();
            assertEquals(4, rsl.length());
            assertFalse(rsl.contains(String.valueOf((char) excludedUnicodeNumbers[0])));
            assertFalse(rsl.contains(String.valueOf((char) excludedUnicodeNumbers[1])));
            assertFalse(rsl.contains(String.valueOf((char) excludedUnicodeNumbers[2])));
            assertFalse(rsl.contains(String.valueOf((char) excludedUnicodeNumbers[3])));
        }
    }
}