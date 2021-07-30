package ru.mortihead.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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