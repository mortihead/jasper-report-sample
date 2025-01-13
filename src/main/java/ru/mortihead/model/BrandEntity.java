package ru.mortihead.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;


@Entity
@Table(name = "brands")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BrandEntity implements Serializable {
    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name_brand")
    private String name;
}