package ru.job4j.urlshortcut.config.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Класс конфигурации Liquibase
 */
@Configuration
public class LiquibaseConfig {

    /**
     * Бин - подключение к базе данных
     * @param driver драйвер БД
     * @param url URL
     * @param username Username
     * @param password Password
     * @return бин для подключения к базе данных
     */
    @Bean
    public DataSource ds(@Value("${spring.datasource.driver-class-name}") String driver,
                         @Value("${spring.datasource.url}") String url,
                         @Value("${spring.datasource.username}") String username,
                         @Value("${spring.datasource.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Бин - миграция базы данных с помощью библиотеки Liquibase
     * @param ds бин подключения к базе данных
     * @param changeLogPath путь к changeLog файлу
     * @return бин для миграции базы данных
     */
    @Bean
    public SpringLiquibase liquibase(DataSource ds,
                                     @Value("${spring.liquibase.change-log}") String changeLogPath) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(ds);
        liquibase.setChangeLog(changeLogPath);
        return liquibase;
    }
}
