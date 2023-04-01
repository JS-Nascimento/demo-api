package dev.jstec.demo.resource.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Hidden
@Schema(name = "Data Transfer Object de SKU" )
public class SkuDto {

    private Integer id;
    private String barcode;

    private String description;

    private Integer stock;

    private BigDecimal price;

    private BigDecimal total_cost;

}
