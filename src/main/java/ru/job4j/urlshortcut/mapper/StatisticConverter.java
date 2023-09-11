package ru.job4j.urlshortcut.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.dto.response.ResponseStatisticDto;

/**
 * Класс используется для конвертации модели Статистика в DTO Статистика
 */
@Component
public class StatisticConverter {

    /**
     * Зависимость от ModelMapper
     */
    private final ModelMapper mapper;

    /**
     * Конструктор.
     * Инициализация объекта ModelMapper
     */
    public StatisticConverter() {
        mapper = new ModelMapper();
    }

    /**
     * Конвертирует объект в DTO
     * @param statistic модель Статистика на входе
     * @return DTO статистика в виде ответа пользователю
     */
    public ResponseStatisticDto convertToDto(Statistic statistic) {
        return mapper.map(statistic, ResponseStatisticDto.class);
    }
}
