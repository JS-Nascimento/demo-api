package dev.jstec.demo.resource.controller;

import dev.jstec.demo.domain.model.Costumer;
import dev.jstec.demo.resource.dto.CostumerDTO;
import dev.jstec.demo.resource.dto.CostumerListDto;
import dev.jstec.demo.service.CostumerService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/costumer")
@Schema(name = "Serviços de Cliente" )
public class CostumerController {
    @Hidden
    private final CostumerService service;

    public CostumerController ( CostumerService service ) {
        this.service = service;
    }
    @Operation(summary = "Obter uma lista resumida para uso em componentes de seleção.", description = "Retorna uma lista resumida de produtos.")
    @GetMapping("/all")
    public ResponseEntity<List<CostumerListDto>> getAll(){

        List<CostumerListDto> result = service.getAlltoList();

        Stream<CostumerListDto> stream = result.stream();

        return ResponseEntity.ok()
                .contentType( MediaType.APPLICATION_JSON )
                .body( stream.collect( Collectors.toList()) );
    }
    @Operation(summary = "Obter uma lista paginada de Clientes.", description = "Retorna uma lista paginada de Clientes.")
    @GetMapping("/allPaged")
    public ResponseEntity<Page <CostumerListDto>> getAllPaged( @RequestParam @Parameter(description = "Número da página a ser exibida.",
                                                                        required = true,
                                                                        in = ParameterIn.QUERY,
                                                                        schema = @Schema(type = "number", format = "integer"),
                                                                        example = "0"
                                                                ) Integer page,
                                                                @RequestParam @Parameter(description = "Quantidade de registros por página.",
                                                                        required = true,
                                                                        in = ParameterIn.QUERY,
                                                                        schema = @Schema(type = "number", format = "integer"),
                                                                        example = "20"
                                                                ) Integer qtyPerPage,
                                                               @RequestParam @Parameter(description = "Atributo pelo qual deve ser ordenada a lista",
                                                                       required= true,
                                                                       in = ParameterIn.QUERY,
                                                                       schema = @Schema(type = "string"),
                                                                       example ="name"
                                                               ) String sortedBy,
                                                               @RequestParam @Parameter(
                                                                       description = "A direção do ordenamento da query.",
                                                                       required = true,
                                                                       in = ParameterIn.QUERY,
                                                                       schema = @Schema(type = "string", allowableValues = {"ASC", "DESC"}),
                                                                       examples = {
                                                                               @ExampleObject(
                                                                                       name = "Ascendente",
                                                                                       value = "ASC"
                                                                               ),
                                                                               @ExampleObject(
                                                                                       name = "Descendente",
                                                                                       value = "DESC"
                                                                               )
                                                                       }
                                                               ) @DefaultValue("ASC") String direction ){
        PageRequest pageRequest = PageRequest.of( page, qtyPerPage, Sort.Direction.valueOf(direction.toUpperCase( )), sortedBy );

        Page<CostumerListDto> response = service.findAllPaged( pageRequest);

        if (response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }

    @Operation(summary = "Obter um Cliente pelo ID.", description = "Retorna um cliente pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Costumer> getById( @PathVariable @Parameter(description = "Id do produto.",
                                                                            required = true,
                                                                            in = ParameterIn.PATH,
                                                                            schema = @Schema(type = "number", format = "long"),
                                                                            example = "20"
                                                                    ) Long id ) {


        return ResponseEntity.ok().body( service.getById( id ) );
    }
    @Operation(summary = "Cria um novo cliente.", description = "Cria um novo cliente e retorna o obejto completo.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Costumer> create (@Parameter(description = "Objeto do tipo Costumer",
                                                required = true,
                                                in = ParameterIn.DEFAULT,
                                                schema = @Schema(type = "array", implementation = Costumer.class)

                                        )@RequestBody Costumer costumer){
        return ResponseEntity.status( HttpStatus.CREATED )
                .body(service.create(costumer));
    }
    @Operation(summary = "Apaga um cliente existente.", description = "Apaga um cliente pelo ID.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable @Parameter(description = "Id do produto.",
                                                required = true,
                                                in = ParameterIn.PATH,
                                                schema = @Schema(type = "number", format = "long"),
                                                example = "20"
                                        ) Long id ){
        service.delete( id );
    }
    @Operation(summary = "Altera um Cliente pelo ID.", description = "Altera um cliente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<CostumerDTO> update (@PathVariable @Parameter(description = "Id do produto.",
                                                required = true,
                                                in = ParameterIn.PATH,
                                                schema = @Schema(type = "number", format = "long"),
                                                example = "20"
                                        ) Long id,
                                               @Parameter(description = "Objeto do tipo Costumer",
                                                required = true,
                                                in = ParameterIn.DEFAULT,
                                                schema = @Schema(type = "array", implementation = CostumerDTO.class)

                                        ) @RequestBody CostumerDTO dto){
        dto = service.update(id, dto);

        return ResponseEntity.ok().body( dto );
    }


}
