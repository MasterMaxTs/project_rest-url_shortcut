package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.RequestUrlDto;
import ru.job4j.urlshortcut.service.url.UrlService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * Контроллер Ссылок URL
 */
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class UrlController {

    /**
     * Зависимоть от сервиса ссылок
     */
    private final UrlService urlService;

    /**
     * Преобразует ссылку на эквивалентное кодовое значение
     * @param urlDto объект UrlDto на входе
     * @return объект ResponseEntity со статусом 200 и телом в виде объекта Map
     */
    @PostMapping("convert")
    public ResponseEntity<?> convert(@Valid @RequestBody RequestUrlDto urlDto) {
        String requestUrl = urlDto.getUrl();
        return ResponseEntity.ok()
                .body(Map.of("code", urlService.convert(requestUrl)));
    }

    /**
     * Выполняет переадресацию по кодовому значению ссылки
     * @param code кодовое значение ссылки на входе
     * @return объект ResponseEntity со статусом 302 с заголовками:
     * HTTP CODE, REDIRECT
     */
    @GetMapping("redirect/{code}")
    public ResponseEntity<?> redirect(@PathVariable("code") String code) {
        Optional<String> optionalUrl = urlService.findUrlByCode(code);
        if (optionalUrl.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Url is not found! Clarify your code.");
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("HTTP CODE", String.valueOf(302))
                .header("REDIRECT", optionalUrl.get())
                .build();
    }
}
