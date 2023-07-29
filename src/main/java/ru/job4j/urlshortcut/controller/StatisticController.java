package ru.job4j.urlshortcut.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.domain.Statistic;
import ru.job4j.urlshortcut.dto.RequestSiteDto;
import ru.job4j.urlshortcut.service.statistic.StatisticService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер Статистики
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StatisticController {

    /**
     * Зависимость от сервиса статистики
     */
    private final StatisticService statisticService;

    /**
     * Предоставляет список статистических данных по доменному имени сайта
     * @param siteDto объект RegistrationSiteDto на входе
     * @return объект ResponseEntity со статусом 200 и телом в виде списка
     * статистических данных
     */
    @GetMapping("statistic")
    public ResponseEntity<?> getStatistics(@Valid @RequestBody RequestSiteDto siteDto) {
        String domainName = siteDto.getSite();
        List<Statistic> statistics = statisticService.getStatistic(domainName);
        return ResponseEntity.ok().body(
                statistics
                        .stream()
                        .map(st -> Map.of(st.getUrl().getUrl(), st.getTotal()))
                        .collect(Collectors.toList())
        );
    }
}
