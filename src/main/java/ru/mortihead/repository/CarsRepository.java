package ru.mortihead.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mortihead.model.CarEntity;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<CarEntity, Integer>, JpaSpecificationExecutor<CarEntity> {
    List<CarEntity> findByNameAndPrice(String name, Integer price);
}
