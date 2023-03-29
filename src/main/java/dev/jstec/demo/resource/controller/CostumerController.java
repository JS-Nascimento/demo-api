package dev.jstec.demo.resource.controller;

import dev.jstec.demo.domain.model.Costumer;
import dev.jstec.demo.resource.dto.CostumerListDto;
import dev.jstec.demo.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/costumer")
public class CostumerController {
    @Autowired
    private CostumerService service;
    @GetMapping("/all")
    @Cacheable
    public ResponseEntity<List<CostumerListDto>> getAll(){

        List<CostumerListDto> result = service.getAlltoList();

        Stream<CostumerListDto> stream = result.stream();

        return ResponseEntity.ok()
                .contentType( MediaType.APPLICATION_JSON )
                .body( stream.collect( Collectors.toList()) );
    }
    @GetMapping("/allPaged")
    public ResponseEntity<Page <CostumerListDto>> getAllPaged( @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(value = "qtyPerPage", defaultValue = "10") Integer qtyPerPage,
                                                               @RequestParam(value = "sortedBy", defaultValue = "name") String sortedBy,
                                                               @RequestParam(value = "sort", defaultValue = "ASC")  String sort){
        PageRequest pageRequest = PageRequest.of( page, qtyPerPage, Sort.Direction.valueOf(sort.toUpperCase( )), sortedBy );

        Page<CostumerListDto> response = service.findAllPaged( pageRequest);

        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Costumer> getById( @PathVariable Long id ){


        return ResponseEntity.ok().body( service.getById( id ) );
    }



}
