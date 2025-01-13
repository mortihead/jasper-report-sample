package ru.mortihead.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RestController;
import ru.mortihead.model.CarEntity;
import ru.mortihead.model.CityEntity;
import ru.mortihead.service.CarService;
import ru.mortihead.service.CityService;
import ru.mortihead.service.ReportService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/", produces = "application/json")
@RequiredArgsConstructor
public class MainController {

    private final CityService cityService;
    private final CarService carsService;
    private final ReportService reportService;

    @GetMapping("/cities")
    @Operation(description = "Получение списка городов")
    public List<CityEntity> getList() {
        return cityService.findAll();
    }

    @GetMapping("/cars")
    @Operation(description = "Получение списка автомобилей")
    public List<CarEntity> getCarsList() {
        return carsService.findAll();
    }

    @GetMapping("/cars/{id}")
    @Operation(description = "Получение запись об автомобиле по id")
    public CarEntity getCarsList(@Parameter(description = "ID автомобиля", example = "1")
                                 @PathVariable("id") Integer carId) {
        return carsService.findById(carId);
    }

    @GetMapping("/download-pdf-simple-report/{brandId}")
    @Operation(description = "Сохранение отчета в PDF")
    public ResponseEntity<Resource> downloadSimpleReportPdf(@Parameter(description = "ID бренда автомобиля", example = "1")
                                                            @PathVariable("brandId") Integer brandId) throws Exception {
        return reportService.downloadPdf(brandId);
    }

    @GetMapping("/download-pdf-multiple-datasources-report")
    @Operation(description = "Сохранение отчета в PDF")
    public ResponseEntity<Resource> downloadMultipleDataSourcesReportPdf() throws Exception {
        return reportService.downloadMultipleDataSourcesReportPdf();
    }


}
