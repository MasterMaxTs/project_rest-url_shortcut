package ru.job4j.urlshortcut.service.credential;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.repository.CredentialCrudRepository;
import ru.job4j.urlshortcut.util.generator.CodeGenerator;

/**
 * Реализация сервиса Учётных данных
 */
@Service
public class CredentialServiceImpl implements CredentialService {

    /**
     * Зависимость от хранилища учётных данных
     */
    private final CredentialCrudRepository store;

    /**
     * Зависимость от генератора кода для login
     */
    private final CodeGenerator loginCodeGenerator;

    /**
     * Зависимость от генератора кода для password
     */
    private final CodeGenerator passwordCodeGenerator;

    /**
     * Конструктор
     * @param store внедрение зависимости от хранилища учётных данных
     * @param loginCodeGenerator внедрение зависимости от генератора кода для login
     * @param passwordCodeGenerator внедрение зависимости от генератора кода
     * для password
     */
    public CredentialServiceImpl(
            CredentialCrudRepository store,
            @Qualifier("loginCodeGenerator") CodeGenerator loginCodeGenerator,
            @Qualifier("passwordCodeGenerator") CodeGenerator passwordCodeGenerator) {
        this.store = store;
        this.loginCodeGenerator = loginCodeGenerator;
        this.passwordCodeGenerator = passwordCodeGenerator;
    }

    @Override
    public Credential save(Credential credential) {
        store.save(credential);
        return credential;
    }

    @Transactional
    @Override
    public Credential generateCredentials() {
        String login = loginCodeGenerator.generate();
        String password = passwordCodeGenerator.generate();
        while (findByLogin(login)) {
            login = loginCodeGenerator.generate();
        }
        while (findByPassword(password)) {
            password = passwordCodeGenerator.generate();
        }
        return new Credential(login, password);
    }

    @Override
    public boolean findByLogin(String login) {
        return store.findByLogin(login).isPresent();
    }

    @Override
    public boolean findByPassword(String password) {
        return store.findByLogin(password).isPresent();
    }
}
