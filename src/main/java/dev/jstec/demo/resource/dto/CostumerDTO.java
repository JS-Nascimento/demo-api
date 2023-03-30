package dev.jstec.demo.resource.dto;

import dev.jstec.demo.domain.CostumerType;
import dev.jstec.demo.domain.model.Address;
import dev.jstec.demo.domain.model.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostumerDTO {
    private Long id;
    private String name;
    private String cnpj;
    private String taxIdentity;
    private String phone;
    private String email;
    private CostumerType costumerType;
    private Address address;
    private String addressNumber;
    private String complement;
    private Instant registerDate;
    private String latitud;
    private String longitud;
    private Seller seller;

}
