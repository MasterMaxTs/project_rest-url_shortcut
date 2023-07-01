package ru.job4j.urlshortcut;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class UrlShortcutApplication {

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

	@Bean
	public SpringLiquibase liquibase(DataSource ds,
									 @Value("${spring.liquibase.change-log}") String changeLogPath) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(ds);
		liquibase.setChangeLog(changeLogPath);
		return liquibase;
	}

	public static void main(String[] args) {
		SpringApplication.run(UrlShortcutApplication.class, args);
	}
}
