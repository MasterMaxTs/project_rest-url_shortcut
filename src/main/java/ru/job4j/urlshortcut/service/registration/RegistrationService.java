package ru.job4j.urlshortcut.service.registration;

import ru.job4j.urlshortcut.domain.Site;

/**
 * Сервис Регистрации сайта в приложении
 */
public interface RegistrationService {

    /**
     * Производит регистрацию сайта в приложении
     * @param domainName доменное имя сайта на входе
     * @return объект Site с проинициализированным id
     */
    Site register(String domainName);
}
