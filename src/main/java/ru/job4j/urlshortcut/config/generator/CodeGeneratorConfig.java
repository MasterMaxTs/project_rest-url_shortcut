package ru.job4j.urlshortcut.config.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.job4j.urlshortcut.util.generator.CodeGenerator;
import ru.job4j.urlshortcut.util.generator.FactoryCodeGenerator;

import java.util.Arrays;

/**
 * Класс конфигурации генераторов кодовой последовательности
 */
@Configuration
public class CodeGeneratorConfig {

    /**
     * Бин - генерор кодовой последовательности для URL сайта
     * @param codeLength требуемая длина кодовой последовательности
     * символов
     * @param startUnicodeNumber начальный символ (нижний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param endUnicodeNumber конечный символ (верхний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param excludedUnicodeNumbers массив символов в значении юникод
     * (из диапазона), неучаствующие в генерации кодовой последовательности
     * @return бин - генерор кодовой последовательности для URL сайта
     */
    @Bean
    public CodeGenerator urlCodeGenerator(
            @Value("${url.code.generator.required.code.length}") String codeLength,
            @Value("${url.code.start.unicode.number}") String startUnicodeNumber,
            @Value("${url.code.end.unicode.number}") String endUnicodeNumber,
            @Value("${url.code.excluded.unicode.numbers}") String[] excludedUnicodeNumbers) {
        int[] excludedNumbers =
                Arrays.stream(excludedUnicodeNumbers).mapToInt(Integer::parseInt).toArray();
        return FactoryCodeGenerator.getCodeGenerator(Integer.parseInt(codeLength),
                                                    Integer.parseInt(startUnicodeNumber),
                                                    Integer.parseInt(endUnicodeNumber),
                                                    excludedNumbers);
    }

    /**
     * Бин - генерор кодовой последовательности для login модели Учётные данные
     * @param codeLength требуемая длина кодовой последовательности
     * символов
     * @param startUnicodeNumber начальный символ (нижний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param endUnicodeNumber конечный символ (верхний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param excludedUnicodeNumbers массив символов в значении юникод
     * (из диапазона), неучаствующие в генерации кодовой последовательности
     * @return бин - генерор кодовой последовательности для login модели Учётные данные
     */
    @Bean
    public CodeGenerator loginCodeGenerator(
            @Value("${login.code.generator.required.code.length}") String codeLength,
            @Value("${login.code.start.unicode.number}") String startUnicodeNumber,
            @Value("${login.code.end.unicode.number}") String endUnicodeNumber,
            @Value("${login.code.excluded.unicode.numbers}") String[] excludedUnicodeNumbers) {
        int[] excludedNumbers =
                Arrays.stream(excludedUnicodeNumbers).mapToInt(Integer::parseInt).toArray();
        return FactoryCodeGenerator.getCodeGenerator(Integer.parseInt(codeLength),
                                                    Integer.parseInt(startUnicodeNumber),
                                                    Integer.parseInt(endUnicodeNumber),
                                                    excludedNumbers);
    }

    /**
     * Бин - генерор кодовой последовательности для password модели Учётные
     * данные
     * @param codeLength требуемая длина кодовой последовательности
     * символов
     * @param startUnicodeNumber начальный символ (нижний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param endUnicodeNumber конечный символ (верхний диапазон)
     * в значении юникод для генерации кодовой последовательности
     * @param excludedUnicodeNumbers массив символов в значении юникод
     * (из диапазона), неучаствующие в генерации кодовой последовательности
     * @return бин - генерор кодовой последовательности для password модели
     * Учётные данные
     */
    @Bean
    public CodeGenerator passwordCodeGenerator(
            @Value("${password.code.generator.required.code.length}") String codeLength,
            @Value("${password.code.start.unicode.number}") String startUnicodeNumber,
            @Value("${password.code.end.unicode.number}") String endUnicodeNumber,
            @Value("${password.code.excluded.unicode.numbers}") String[] excludedUnicodeNumbers) {
        int[] excludedNumbers =
                Arrays.stream(excludedUnicodeNumbers).mapToInt(Integer::parseInt).toArray();
        return FactoryCodeGenerator.getCodeGenerator(Integer.parseInt(codeLength),
                                                    Integer.parseInt(startUnicodeNumber),
                                                    Integer.parseInt(endUnicodeNumber),
                                                    excludedNumbers);
    }
}
