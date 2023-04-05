package dev.jstec.demo.resource.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import dev.jstec.demo.service.AmazonS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFromS3(@RequestParam String bucketName,
                                                              @RequestParam String objectName,
                                                              @RequestParam String downloadPath) {

        AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();

        try {
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, objectName));
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            File downloadedFile = new File(downloadPath);

            FileUtils.copyInputStreamToFile(inputStream, downloadedFile);

            return ResponseEntity.ok()
                    .contentLength(downloadedFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadedFile.getName() + "\"")
                    .body(new InputStreamResource(new FileInputStream(downloadedFile)));

        } catch (AmazonServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
