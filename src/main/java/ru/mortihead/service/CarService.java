package ru.mortihead.service;

import ru.mortihead.model.CarEntity;
import java.util.List;

public interface CarService {
    List<CarEntity> findAll();
}
