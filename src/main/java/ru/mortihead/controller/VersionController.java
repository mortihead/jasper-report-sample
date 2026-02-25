package ru.mortihead.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST контроллер для получения информации о версии приложения.
 */
@RestController
@RequestMapping(value = "api/v1/", produces = "application/json")
@RequiredArgsConstructor
public class VersionController {

    @Value("${application.version}")
    private String applicationVersion;

    /**
     * Возвращает текущую версию приложения из pom.xml.
     *
     * @return JSON объект с версией приложения
     */
    @GetMapping("/version")
    @Operation(description = "Получение версии приложения")
    public Map<String, String> getVersion() {
        return Map.of("version", applicationVersion);
    }

}
