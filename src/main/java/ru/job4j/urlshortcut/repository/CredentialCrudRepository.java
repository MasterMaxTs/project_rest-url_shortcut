package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.Credential;

import java.util.Optional;

/**
 * Хранилище Учётных данных
 */
public interface CredentialCrudRepository
                                extends CrudRepository<Credential, Integer> {
    /**
     * Выполняет поиск учётных данных в хранилище по логину
     * @param login логин
     * @return результат поиска в виде Optional
     */
    Optional<Credential> findByLogin(String login);

    /**
     * Выполняет поиск учётных данных в хранилище по паролю
     * @param password пароль
     * @return результат поиска в виде Optional
     */
    Optional<Credential> findByPassword(String password);
}
