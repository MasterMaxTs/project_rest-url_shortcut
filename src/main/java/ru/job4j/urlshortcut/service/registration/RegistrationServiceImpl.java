package ru.job4j.urlshortcut.service.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Credential;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.service.credential.CredentialService;
import ru.job4j.urlshortcut.service.site.SiteService;

/**
 * Реализация сервиса Регистрации сайта в приложении
 */
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    /**
     * Зависимость от сервиса сайтов
     */
    private final SiteService siteService;

    /**
     * Зависимость от сервиса учётных данных
     */
    private final CredentialService credentialService;

    /**
     * Зависимость от BCryptPasswordEncoder
     */
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Site register(String domainName) {
        Credential credentials = credentialService.generateCredentials();
        String rawPassword = credentials.getPassword();
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        Credential credentialInDb = credentialService.save(credentials);
        Site site = siteService.save(
                new Site(domainName, true, credentialInDb));
        site.getCredential().setPassword(rawPassword);
        return site;
    }
}
