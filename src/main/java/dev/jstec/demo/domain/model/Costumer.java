package dev.jstec.demo.domain.model;

import dev.jstec.demo.domain.CostumerType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.Instant;

@Entity
@Table
@Data
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String cnpj;
    private String taxIdentity;
    private String phone;
    private String email;


    @Enumerated(EnumType.STRING)
    private CostumerType costumerType;
    @ManyToOne
    @JoinColumn( name  = "address_id")
    private Address address;

    private String addressNumber;

    private String complement;
    private Instant registerDate;
    private String latitud;
    private String longitud;
    @ManyToOne
    @JoinColumn( name  = "seller_id")
    private Seller seller;

}

