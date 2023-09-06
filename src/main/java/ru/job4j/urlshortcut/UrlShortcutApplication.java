package ru.job4j.urlshortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный исполняемый класс приложения
 */
@SpringBootApplication
public class UrlShortcutApplication {

	/**
	 * Главный исполняемый метод приложения Сервис коротких ссылок
	 * @param args массив из аргументов командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(UrlShortcutApplication.class, args);
	}
}
