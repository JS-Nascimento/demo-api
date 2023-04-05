package dev.jstec.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import dev.jstec.demo.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    public final String bucketNameS3 = "jstec-bucket";
    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(String fileName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3.putObject(bucketNameS3, fileName, file.getInputStream(), metadata);
        return amazonS3.getUrl(bucketNameS3, fileName).toString();
    }


    public void deleteFile(String fileName) {
        amazonS3.deleteObject(bucketNameS3, fileName);
    }
}

