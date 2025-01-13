package ru.mortihead.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mortihead.model.BrandEntity;
import ru.mortihead.model.CarEntity;
import ru.mortihead.repository.BrandsRepository;
import ru.mortihead.repository.CarsRepository;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private final CarsRepository   carsRepo;
    @Autowired
    private final BrandsRepository brandRepo;

    public CarServiceImpl(CarsRepository carsRepo, BrandsRepository brandRepo) {
        this.carsRepo = carsRepo;
        this.brandRepo = brandRepo;
    }


    @Override
    @Transactional
    public List<CarEntity> findAll() {
        return carsRepo.findAll();
    }

    @Override
    @Transactional
    public List<CarEntity> findByBrandId(Integer brandId) {
        if (Objects.isNull(brandId) || brandId.equals(-1)) {
            return carsRepo.findAll();
        } else {
            BrandEntity brand = brandRepo.findById(brandId).orElseThrow(() -> new IllegalStateException("Brand id: " + brandId + " не найден!"));
            return carsRepo.findByBrand(brand);
        }
    }

    @Override
    public CarEntity findById(Integer carId) {
        return carsRepo.findById(carId).orElseThrow(() -> new IllegalStateException("Car id: " + carId + " not found!"));
    }

}
