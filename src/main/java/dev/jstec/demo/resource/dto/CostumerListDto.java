package dev.jstec.demo.resource.dto;

import dev.jstec.demo.domain.model.Costumer;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Hidden
public class CostumerListDto {

    public CostumerListDto( Costumer costumer ){
        this.id = costumer.getId();
        this.name = costumer.getName();
        this.taxIdentity = costumer.getTaxIdentity();
        this.cnpj = costumer.getCnpj();
    }



    private Long id;
    private String name;
    private String taxIdentity;
    private String cnpj;

}
