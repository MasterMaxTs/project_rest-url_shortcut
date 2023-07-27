package ru.job4j.urlshortcut.service.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.repository.CredentialCrudRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Реализация UserDetailsService Spring Security
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    /**
     * Зависимость от хранилища Регистрационных данных
     */
    private final CredentialCrudRepository store;

    /**
     * Находит регистрационные данные пользователя в БД, формирует по ним
     * объект UserDetails
     * @param username регистрационное имя пользователя на входе
     * @return объект UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username)
                                            throws UsernameNotFoundException {
        Optional<Credential> registrationOptional = store.findByLogin(username);
        if (registrationOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        Credential credential = registrationOptional.get();
        return new User(credential.getLogin(),
                        credential.getPassword(),
                        emptyList());
    }
}
