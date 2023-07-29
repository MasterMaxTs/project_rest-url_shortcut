package ru.job4j.urlshortcut.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.urlshortcut.dto.error.AppErrorDto;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Класс используется для глобальной обработки исключений,
 * которые могут возникнуть во всех контроллерах приложения
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Метод глобально отлавливает и обрабатывает исключения типа
     * NoSuchElementException, возникающих в контроллерах, меняет статус и тело ответа
     * @param ex Exception
     * @return объект ResponseEntity со статусом 404 и телом в виде
     * объекта-ошибки AppErrorDto
     */
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new AppErrorDto(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        ex.getClass().getName()
                ));
    }

    /**
     * Метод глобально отлавливает и обрабатывает исключения типа
     * MethodArgumentNotValidException, возникающих в контроллерах при валидации
     * моделей, меняет статус и тело ответа
     * @param ex MethodArgumentNotValidException
     * @return объект ResponseEntity со статусом 400 и телом в виде
     * списка ошибок
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
                                        MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getFieldErrors()
                        .stream()
                        .map(fe -> Map.of(
                                fe.getField(),
                                String.format("%s. Actual value: %s",
                                        fe.getDefaultMessage(),
                                        fe.getRejectedValue()))
                        ).collect(Collectors.toList()));
    }
}
