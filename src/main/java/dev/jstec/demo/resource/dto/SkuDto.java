package dev.jstec.demo.resource.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class SkuDto {

    private Integer id;
    private String barcode;

    private String description;

    private Integer stock;

    private BigDecimal price;

    private BigDecimal total_cost;

}
