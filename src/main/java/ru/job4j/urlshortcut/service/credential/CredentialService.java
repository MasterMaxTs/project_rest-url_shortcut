package ru.job4j.urlshortcut.service.credential;

import ru.job4j.urlshortcut.domain.Credential;

/**
 * Сервис Учётных данных
 */
public interface CredentialService {

    /**
     * Рандомно генерирует аутентификационные данные 
     * @return объект Registration
     */
    Credential generateCredentials();

    /**
     * Сохраняет модель в хранилище
     * @return модель учётных данные с проинициализированным id
     */
    Credential save(Credential credential);

    /**
     * Выполняет поиск модели в хранилище по login
     * @param login логин
     * @return результат поиска в виде boolean
     */
    boolean findByLogin(String login);

    /**
     * Выполняет поиск модели в хранилище по password
     * @param password пароль
     * @return результат поиска в виде boolean
     */
    boolean findByPassword(String password);
}
