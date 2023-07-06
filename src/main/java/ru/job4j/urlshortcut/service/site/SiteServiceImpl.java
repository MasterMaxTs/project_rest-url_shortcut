package ru.job4j.urlshortcut.service.site;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.Site;
import ru.job4j.urlshortcut.repository.SiteCrudRepository;

/**
 * Реализация сервиса Зарегистрированных в приложении сайтов
 */
@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    /**
     * Зависимость от хранилища зарегистрированных сайтов
     */
    private final SiteCrudRepository store;


    @Override
    public boolean isRegister(String url) {
        return store.findByUrl(url).isPresent();
    }

    @Override
    public Site save(Site site) {
        return store.save(site);
    }

    @Override
    public boolean update(Site site) {
        return false;
    }

    @Override
    public boolean deleteByUrl(String url) {
        return false;
    }
}
