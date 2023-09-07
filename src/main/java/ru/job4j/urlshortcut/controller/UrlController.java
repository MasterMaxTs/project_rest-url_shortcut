package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.dto.request.RequestUrlDto;
import ru.job4j.urlshortcut.service.url.UrlService;

import javax.validation.Valid;
import java.util.Map;

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
     * @return объект ResponseEntity со статусом 302 с заголовком Location
     */
    @GetMapping("redirect/{code}")
    public ResponseEntity<?> redirect(@PathVariable("code") String code) {
        Url urlInDb = urlService.findUrlByCode(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", urlInDb.getUrl())
                .build();
    }
}
