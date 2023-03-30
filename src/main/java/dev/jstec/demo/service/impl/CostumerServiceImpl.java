package dev.jstec.demo.service.impl;

import dev.jstec.demo.domain.CostumerType;
import dev.jstec.demo.domain.model.Address;
import dev.jstec.demo.domain.model.Costumer;
import dev.jstec.demo.domain.model.Seller;
import dev.jstec.demo.domain.repository.CostumerRepository;
import dev.jstec.demo.exception.DatabaseIntegrityException;
import dev.jstec.demo.exception.EntityNotFoundException;
import dev.jstec.demo.exception.ResourceNotFoundException;
import dev.jstec.demo.resource.dto.CostumerDTO;
import dev.jstec.demo.resource.dto.CostumerListDto;
import dev.jstec.demo.service.CostumerService;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CostumerServiceImpl implements CostumerService {
    private final CostumerRepository repository;
    private final  ModelMapper modelMapper;

    public CostumerServiceImpl ( CostumerRepository repository, ModelMapper modelMapper ) {
        this.repository = repository;
        this.modelMapper =modelMapper;
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

    @Override
    public Costumer create ( Costumer costumer ) {
        return repository.save( costumer );
    }

    @Override
    public void delete ( Long id ) {
        try {
            repository.deleteById( id );

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException( "Cliente não encontrada." );
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException( "Violação de integridade relacional.");
        }

    }

    @Override
    public CostumerDTO update ( Long id, CostumerDTO dto ) {
        try {

            return modelMapper.map( repository.save( costumerMappertoDto( id, dto ) ) , CostumerDTO.class );

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException( "Cliente não encontrada." );
        }
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

    private Costumer costumerMappertoDto(Long id, CostumerDTO dto){
        Costumer costumer = repository.getReferenceById( id );
        costumer.setName( dto.getName() );
        costumer.setAddress( dto.getAddress() );
        costumer.setCnpj( dto.getCnpj() );
        costumer.setTaxIdentity( dto.getTaxIdentity() );
        costumer.setPhone( dto.getPhone() );
        costumer.setEmail( dto.getEmail() );
        costumer.setCostumerType( dto.getCostumerType() );
        costumer.setAddressNumber( dto.getAddressNumber() );
        costumer.setComplement( dto.getComplement() );
        costumer.setRegisterDate( dto.getRegisterDate() );
        costumer.setSeller( dto.getSeller() );
        costumer.setLatitud( dto.getLatitud() );
        costumer.setLongitud( dto.getLongitud() );
        return costumer;
    }
}
