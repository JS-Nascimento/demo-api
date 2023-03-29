package dev.jstec.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(precision = 5, scale = 2)
    private BigDecimal comission_rate;
    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List <Costumer> costumerList = new ArrayList <>();

}
