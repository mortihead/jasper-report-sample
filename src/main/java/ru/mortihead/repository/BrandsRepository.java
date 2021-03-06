package ru.mortihead.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mortihead.model.BrandEntity;
import ru.mortihead.model.CarEntity;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<BrandEntity, Integer>, JpaSpecificationExecutor<CarEntity> {
}
