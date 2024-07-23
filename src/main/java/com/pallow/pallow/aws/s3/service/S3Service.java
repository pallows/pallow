package com.pallow.pallow.aws.s3.service;

import com.pallow.pallow.aws.s3.uploader.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Uploader s3Uploader;

    public void upload(String name, MultipartFile file) throws IOException {
        String url = "";
        if (!file.isEmpty()) {
            url = s3Uploader.upload(file,"static/users");
        }
    }

}
