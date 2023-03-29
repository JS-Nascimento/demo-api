package dev.jstec.demo.resource.controller;

import dev.jstec.demo.domain.model.Sku;
import dev.jstec.demo.resource.dto.SkuDto;
import dev.jstec.demo.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sku")
public class SkuController {
    @Autowired
    private SkuService service;

    @GetMapping("/all")
    public ResponseEntity<List <Sku>> getAll(){
        return ResponseEntity.status( 200 ).body( service.getAll() );
    }

    @GetMapping("/allPaged")
    public ResponseEntity<Page <Sku>> getAllPaged( @RequestParam Integer qtyPerPage,
                                                   @RequestParam String sortedBy,
                                                   @RequestParam @DefaultValue("ASC") String direction ){

        Page<Sku> response = service.findAllPaged(qtyPerPage,sortedBy, direction);

        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sku> getById( @PathVariable Long id ){

        Sku sku = service.getById(id);

        if (sku == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( sku );
    }

    @GetMapping("/description/{name}")
    public ResponseEntity<List <Sku>> getByDescription( @PathVariable String name ){
        List<Sku> response = service.findByDescription( name );
        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }

    @GetMapping("/topStock")
    public ResponseEntity<List <SkuDto>> getTopByStock(@RequestParam Integer maxItens){
        List<SkuDto> response = service.getTopByStock(maxItens!=null?maxItens:10);
        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }
}
