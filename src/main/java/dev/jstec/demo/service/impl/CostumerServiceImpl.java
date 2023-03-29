package dev.jstec.demo.service.impl;

import dev.jstec.demo.domain.model.Costumer;
import dev.jstec.demo.domain.repository.CostumerRepository;
import dev.jstec.demo.resource.dto.CostumerListDto;
import dev.jstec.demo.service.CostumerService;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CostumerServiceImpl implements CostumerService {
    private final CostumerRepository repository;

    public CostumerServiceImpl ( CostumerRepository repository ) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true, timeoutString = "120")
    public List <CostumerListDto> getAlltoList () {

        List<CostumerListDto> result = repository.getAllForList().stream().map(objArr -> {
            CostumerListDto dto = new CostumerListDto();
            dto.setId((Long) objArr[0]);
            dto.setName((String) objArr[1]);
            dto.setCnpj((String) objArr[2]);
            dto.setTaxIdentity((String) objArr[3]);
            return dto;
        }).collect(Collectors.toList());

        return result;
    }



    @Transactional(readOnly = true)
    public Page <CostumerListDto> findAllPaged( PageRequest pageRequest ) {

        Page <Costumer> result = repository.findAll(pageRequest);

                return result.map( CostumerListDto::new );
    }
    @Transactional(readOnly = true)
    @Override
    public Costumer getById ( Long id ) {
        Optional <Costumer> result = repository.findById( id );

        if(result.isEmpty()){
            return null;
        }

        return result.map(  costumer  -> {
            costumer.setId( result.get().getId() );
            costumer.setCostumerType( result.get().getCostumerType() );
            costumer.setCnpj( result.get().getCnpj() );
            costumer.setAddress( result.get().getAddress() );
            costumer.setEmail( result.get().getEmail() );
            costumer.setComplement( result.get().getComplement() );
            costumer.setLatitud( result.get().getLatitud() );
            costumer.setLongitud( result.get().getLongitud() );
            costumer.setName( result.get().getName() );
            costumer.setPhone( result.get().getPhone() );
            costumer.setAddressNumber( result.get().getAddressNumber() );
            costumer.setRegisterDate( result.get().getRegisterDate() );
            costumer.setSeller( result.get().getSeller() );
            costumer.setTaxIdentity( result.get().getTaxIdentity() );
            return costumer;

        } ).orElseThrow( () -> {
            throw new DataAccessResourceFailureException( "Recurso nao encontrado." )
                    ;} );
    }

    @Override
    @Transactional(readOnly = true, timeoutString = "120")
    public List <Costumer> getAll () {

        return repository.findAll();
    }
}
