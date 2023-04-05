package dev.jstec.demo.resource.controller;

import dev.jstec.demo.service.AmazonS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/amazonS3")
@Schema(
        name = "AmazonS3",
        description = "Serviços de manipulação de arquivos no S3 da Amazon"
)
public class AmazonS3Controller {


    private final AmazonS3Service service;

    public AmazonS3Controller(AmazonS3Service service) {
        this.service = service;
    }


    @Operation(summary = "Fazer upload de um arquivo para o S3 da Aws.", description = "Fazer upload de um arquivo e retorna o nome do arquivo.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFilesToS3(@RequestParam @Parameter(description = "Nome do Arquivo.",
            required = true,
            example = "arquivo_teste.jpg") String fileName,
                                  @Parameter(description = "Arquivo para ser carregado no S3",
                                          required = true,
                                          schema = @Schema(type = "string", format = "binary"))
                                  @RequestPart(value = "file") MultipartFile file) {
        try {
            return service.uploadFile(fileName, file);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Operation(summary = "Fazer delete de um arquivo no S3 da Aws.", description = "Apagar um arquivo no S3.")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilesToS3(@RequestParam @Parameter(description = "Nome do Arquivo.",
            required = true,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string"),
            example = "arquivo_teste.jpg"
    ) String fileName) throws Exception {
        try {
            service.deleteFile(fileName);

        } catch (Exception e) {
            throw new Exception("Erro desconhecido");
        }

    }

}
