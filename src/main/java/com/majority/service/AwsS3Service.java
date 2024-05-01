package com.majority.service;

import com.amazonaws.services.s3.model.PresignedUrlDownloadRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.majority.configuration.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@ConfigurationProperties(prefix = "aws")
public class AwsS3Service {
    @Autowired
    private AwsConfig awsConfig;

    @Value("${aws.bucketName}")
    private String bucketName;

    public String saveImage(byte[] image){
        AmazonS3 awsS3 = awsConfig.amazonS3();
        File file = new File("Teste2.jpeg");
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        awsS3.putObject(bucketName, "Teste2", file);
        return "funcionou";
    }

    public byte[] getImage(){
        AmazonS3 awsS3 = awsConfig.amazonS3();

        S3Object getObjectResult = awsS3.getObject(bucketName, "Teste2");
        S3ObjectInputStream inputStream = getObjectResult.getObjectContent();

        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
