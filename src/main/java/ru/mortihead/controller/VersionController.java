package ru.mortihead.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/", produces = "application/json")
@RequiredArgsConstructor
public class VersionController {

    @Value("${application.version}")
    private String applicationVersion;

    @GetMapping("/version")
    @Operation(description = "Получение версии приложения")
    public String getVersion() {
        return applicationVersion;
    }

}
