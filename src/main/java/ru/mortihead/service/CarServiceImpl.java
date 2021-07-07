package ru.mortihead.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mortihead.model.CarEntity;
import ru.mortihead.repository.CarsRepository;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Service
public class CarServiceImpl implements CarService {

    private final CarsRepository carsRepo;

    @Override
    @Transactional
    public List<CarEntity> findAll() {
        return carsRepo.findAll();
    }
}
