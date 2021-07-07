package ru.mortihead.service;

import ru.mortihead.model.CityEntity;

import java.util.List;

public interface CityService {
    List<CityEntity> findAll();
}
