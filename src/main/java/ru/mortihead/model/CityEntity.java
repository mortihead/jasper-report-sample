package ru.mortihead.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "city")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CityEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name_city")
    private String name;

    @Column(name = "population")
    private Integer population;

}