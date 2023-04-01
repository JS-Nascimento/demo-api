package dev.jstec.demo.resource.controller;

import dev.jstec.demo.domain.model.Sku;
import dev.jstec.demo.resource.dto.SkuDto;
import dev.jstec.demo.service.SkuService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sku")
@Schema(name = "Serviços de Produto" )
public class SkuController {
    @Autowired
    private SkuService service;

    @Hidden
    @GetMapping("/all")
    public ResponseEntity <List <Sku>> getAll () {
        return ResponseEntity.status( 200 ).body( service.getAll() );
    }

    @Operation(summary = "Obter uma lista paginada de Produtos.", description = "Retorna uma lista paginada de produtos.")
    @GetMapping("/allPaged")
    public ResponseEntity <Page <Sku>> getAllPaged ( @RequestParam @Parameter(description = "Quantidade de registros por página.",
                                                                                required = true,
                                                                                in = ParameterIn.QUERY,
                                                                                schema = @Schema(type = "number", format = "integer"),
                                                                                example = "20"
                                                                        ) Integer qtyPerPage,
                                                     @RequestParam @Parameter(description = "Atributo pelo qual deve ser ordenada a lista",
                                                                                 required= true,
                                                                                 in = ParameterIn.QUERY,
                                                                                 schema = @Schema(type = "string"),
                                                                                 example ="description"
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
                                                     ) @DefaultValue("ASC") String direction ) {

        Page <Sku> response = service.findAllPaged( qtyPerPage, sortedBy, direction );

        if (response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }

    @Operation(summary = "Obter um produto pelo ID.", description = "Retorna um registro completo de produtos.")
    @GetMapping("/{id}")
    public ResponseEntity <Sku> getById ( @PathVariable @Parameter(description = "Id do produto.",
                                                                    required = true,
                                                                    in = ParameterIn.PATH,
                                                                    schema = @Schema(type = "number", format = "long"),
                                                                    example = "20"
                                                            ) Long id ) {

        Sku sku = service.getById( id );

        if (sku == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( sku );
    }
    @Operation(summary = "Obter uma lista de produtos pela fração da descrição.", description = "Retorna uma lista de produtos pela fração da descrição.")
    @GetMapping("/description/{name}")
    public ResponseEntity <List <Sku>> getByDescription ( @PathVariable @Parameter(description = "Fração da descrição do produto",
                                                            required = true,
                                                            in = ParameterIn.PATH,
                                                            schema = @Schema(type = "string"),
                                                            example = "caneta" ) String name ) {
        List <Sku> response = service.findByDescription( name );
        if (response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }
    @Operation(summary = "Obter uma lista de produtos que tem maior valor monetário em estoque.", description = "Retorna uma lista de produtos que tem maior valor monetário em estoque.")
    @GetMapping("/topStock")
    public ResponseEntity <List <SkuDto>> getTopByStock ( @Parameter(description = "Quantidade de registros retornados. se não informado retorna padrão 10 produtos com maior estoque",
                                                                                    required = false,
                                                                                    in = ParameterIn.QUERY,
                                                                                    schema = @Schema(type = "number", format = "integer"),
                                                                                    example = "15"
                                                                            )  @RequestParam Integer maxItens ) {
        List <SkuDto> response = service.getTopByStock( maxItens != null ? maxItens : 10 );
        if (response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( response );
    }
}
