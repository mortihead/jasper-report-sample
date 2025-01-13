package ru.mortihead.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mortihead.model.CityEntity;
import ru.mortihead.repository.CityRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private final CityRepository cityRepo;

    public CityServiceImpl(CityRepository cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Override
    @Transactional
    public List<CityEntity> findAll() {
        return cityRepo.findAll();
    }
}
