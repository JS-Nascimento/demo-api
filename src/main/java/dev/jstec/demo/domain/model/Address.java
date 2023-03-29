package dev.jstec.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetName;

    private String district;

    private String city;

    private String state;

    private String zipCode;
    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List <Costumer> costumerList = new ArrayList <>();

}
