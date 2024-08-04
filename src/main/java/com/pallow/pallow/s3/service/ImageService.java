package com.pallow.pallow.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final AmazonS3 amazonS3;
    private final String bucketName = "pallowbucket";

    public ImageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

//    public String imageUpload(MultipartRequest request) throws IOException {
//        // request 에서 이미지 파일을 가져옴
//        MultipartFile file = request.getFile("file");
//
//        // 가져온 이미지 파일의 이름과 확장자 추출
//        String fileName = file.getOriginalFilename();
//        String ext = fileName.substring(fileName.indexOf("."));
//
//        // 이미지 파일에 고유 아이디 생성
//        String uuidFileName = UUID.randomUUID() + ext;
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uuidFileName, file.getInputStream(), metadata);
//
//        amazonS3.putObject(putObjectRequest);
//        return amazonS3.getUrl(bucketName, fileName).toString();
//    }

    public String imageUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID() + ext;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uuidFileName, file.getInputStream(), metadata);
        amazonS3.putObject(putObjectRequest);

        return amazonS3.getUrl(bucketName, uuidFileName).toString();
    }

    public void deleteImage(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucketName, fileName);
    }
}
