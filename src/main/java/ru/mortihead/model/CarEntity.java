package ru.mortihead.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "cars")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name_car")
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @Column(name = "price")
    private Integer price;

}