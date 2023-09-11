package ru.job4j.urlshortcut.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.dto.response.ResponseRegistrationDto;

/**
 * Класс используется для конвертации модели Сайт в DTO регистрация сайта
 */
@Component
public class RegistrationConverter {

    /**
     * Зависимость от ModelMapper
     */
    private final ModelMapper mapper;

    /**
     * Конструктор.
     * Инициализация объекта ModelMapper
     */
    public RegistrationConverter() {
        mapper = new ModelMapper();
        mapper.createTypeMap(Site.class, ResponseRegistrationDto.class)
                .addMapping(s -> s.getCredential().getLogin(),
                        ResponseRegistrationDto::setLogin)
                .addMapping(s -> s.getCredential().getPassword(),
                        ResponseRegistrationDto::setPassword);
    }

    /**
     * Конвертирует объект в DTO
     * @param site модель Сайт на входе
     * @return DTO регистрация сайта в виде ответа пользователю
     */
    public ResponseRegistrationDto convertToDto(Site site) {
        return mapper.map(site, ResponseRegistrationDto.class);
    }
}
