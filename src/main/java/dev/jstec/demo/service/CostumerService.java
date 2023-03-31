package dev.jstec.demo.service;

import dev.jstec.core.service.BaseService;
import dev.jstec.demo.domain.model.Costumer;
import dev.jstec.demo.resource.dto.CostumerDTO;
import dev.jstec.demo.resource.dto.CostumerListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CostumerService extends BaseService<Costumer> {

    List <CostumerListDto> getAlltoList ();

    Page <CostumerListDto> findAllPaged ( PageRequest pageRequest );

    Costumer create ( Costumer costumer );

    void delete (Long id);

    CostumerDTO update ( Long id, CostumerDTO dto );
}
