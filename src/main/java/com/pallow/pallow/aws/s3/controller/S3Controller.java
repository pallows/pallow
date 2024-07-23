package com.pallow.pallow.aws.s3.controller;

import com.pallow.pallow.aws.s3.service.S3Service;
import com.pallow.pallow.aws.s3.uploader.S3Uploader;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(path = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponseDto> uploadUserFile(
            @RequestPart(value = "name") String name,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        s3Service.upload(name, file);
        return ResponseEntity.ok(new CommonResponseDto(Message.ACCEPT_APPLY_SUCCESS));

    }

}
