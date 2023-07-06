package ru.job4j.urlshortcut.service.url;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.repository.UrlCrudRepository;
import ru.job4j.urlshortcut.util.generator.CodeGenerator;

/**
 * Реализация сервиса уникальных кодов для URl сайтов
 */
@Service
public class UrlServiceImpl implements UrlService {

    /**
     * Зависимость от хранилища кодов для URL сайтов
     */
    private final UrlCrudRepository store;

    /**
     * Зависимость от генератора кода для URL сайта
     */
    private final CodeGenerator codeGenerator;

    /**
     * Конструктор
     * @param store внедрение зависимость от хранилища кодов для URL сайтов
     * @param codeGenerator внедрение зависимости от генератора кода для URL сайта
     */
    public UrlServiceImpl(UrlCrudRepository store,
                          @Qualifier("urlCodeGenerator") CodeGenerator codeGenerator) {
        this.store = store;
        this.codeGenerator = codeGenerator;
    }

    @Override
    @Transactional
    public Url save(Url url) {
        String code = codeGenerator.generate();
        while (findByUrlCode(code)) {
            code = codeGenerator.generate();
        }
        url.setCode(code);
        store.save(url);
        return url;
    }

    @Override
    public boolean findByUrlCode(String code) {
        return store.findByCode(code).isPresent();
    }
}
