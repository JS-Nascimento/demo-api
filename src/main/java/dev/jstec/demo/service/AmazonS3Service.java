package dev.jstec.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AmazonS3Service {

    String uploadFile(String fileName, MultipartFile file) throws IOException;

    void deleteFile(String fileName);

}

