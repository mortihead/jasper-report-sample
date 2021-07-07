package ru.mortihead.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mortihead.model.CityEntity;
import ru.mortihead.repository.CityRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepo;

    @Override
    @Transactional
    public List<CityEntity> findAll() {
        return cityRepo.findAll();
    }
}
