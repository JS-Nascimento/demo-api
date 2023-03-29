package dev.jstec.demo.service.impl;

import dev.jstec.demo.domain.model.Sku;
import dev.jstec.demo.domain.repository.SkuRepository;
import dev.jstec.demo.resource.dto.SkuDto;
import dev.jstec.demo.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuRepository skuRepository;
    @Override
    public List <Sku> getAll(){
        return skuRepository.findAll();
    }

    @Override
    public Sku getById ( Long id ) {
       Optional <Sku> result = skuRepository.findById( id );


       if(result.isEmpty()){
           return null;
       }

        return result.map(  sku  -> {
            sku.setId( result.get().getId() );
            sku.setBarcode( result.get().getBarcode() );
            sku.setDescription( result.get().getDescription() );
            sku.setSkuStock( result.get().getSkuStock() );
            return sku;

        } ).orElseThrow( () -> {
                        throw new DataAccessResourceFailureException( "Recurso nao encontrado." )
                                ;} );
    }



    @Override
    public List <Sku> findByDescription ( String description ) {
        return skuRepository.findByDescriptionContainsIgnoreCaseOrderByDescription(description);
    }
    @Override
    public Page <Sku> findAllPaged( Integer qtyPerPage, String sortedBy, String sort ){
        Pageable pageable = PageRequest.of( 0, qtyPerPage,
                                sort.equalsIgnoreCase( "DESC" )?Sort.by(sortedBy).descending():Sort.by(sortedBy).ascending() );


        return skuRepository.findAll(pageable);

    }
    @Override
    public List <SkuDto> getTopByStock ( Integer max) {
        Pageable pageable  = PageRequest.of( 0, 20 );

        List<Object[]> result =  skuRepository.getMaxStock(max);

      return result.stream()
                .map( x -> {
                    SkuDto skuDto = new SkuDto();
                    skuDto.setId((Integer) x[0]);
                    skuDto.setBarcode((String) x[1]);
                    skuDto.setDescription((String) x[2]);
                    skuDto.setPrice((BigDecimal) x[3]);
                    skuDto.setStock((Integer) x[4]);
                    skuDto.setTotal_cost((BigDecimal) x[5]);

                    return skuDto;
                }).collect( Collectors.toList());


    }

}
