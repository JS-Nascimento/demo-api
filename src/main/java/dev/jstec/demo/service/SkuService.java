package dev.jstec.demo.service;

import dev.jstec.core.service.BaseService;
import dev.jstec.demo.domain.model.Sku;
import dev.jstec.demo.resource.dto.SkuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SkuService extends BaseService <Sku> {



    Sku getById ( Long id );

    List <Sku> findByDescription( String description);

    Page <Sku> findAllPaged( Integer qtyPerPage, String sortedBy, String sort );

    List <SkuDto> getTopByStock ( Integer max);
}
