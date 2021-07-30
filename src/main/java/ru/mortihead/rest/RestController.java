package ru.mortihead.rest;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.ApiOperation;
import ru.mortihead.model.BrandEntity;
import ru.mortihead.model.CarEntity;
import ru.mortihead.model.CityEntity;
import ru.mortihead.service.CarService;
import ru.mortihead.service.CityService;
import ru.mortihead.service.ReportService;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "api/v1/", produces = "application/json")
public class RestController {

    private final CityService cityService;
    private final CarService    carsService;
    private final ReportService reportService;

    @GetMapping("/cities")
    @ApiOperation("Получение списка городов")
    public List<CityEntity> getList() {
        return cityService.findAll();
    }

    @GetMapping("/cars")
    @ApiOperation("Получение списка автомобилей")
    public List<CarEntity> getCarsList() {
        return carsService.findAll();
    }

    @GetMapping("/cars/{id}")
    @ApiOperation("Получение запись об автомобиле по id")
    public CarEntity getCarsList(@ApiParam(value = "ID автомобиля", example = "1")
                                           @PathVariable("id") Integer carId) {
        return carsService.findById(carId);
    }

    @GetMapping("/download-pdf/{brandId}")
    @ApiOperation("Сохранение отчета в PDF")
    public ResponseEntity<Resource> downloadPdf(@ApiParam(value = "ID бренда автомобиля", example = "1")
                                                    @PathVariable("brandId") Integer brandId) throws Exception {
        return reportService.downloadPdf(brandId);
    }

}
