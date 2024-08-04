package com.pallow.pallow.s3.controller;

import com.pallow.pallow.s3.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

//    @PostMapping("/image/upload")
//    @ResponseBody
//    public Map<String, Object> imageUpload(MultipartRequest request) {
//        Map<String, Object> responseData = new HashMap<>();
//        try {
//            String s3Url = imageService.imageUpload(request);
//            responseData.put("uploaded", true);
//            responseData.put("url", s3Url);
//            return responseData;
//        } catch (IOException e) {
//            responseData.put("uploaded", false);
//            return responseData;
//        }
//
//    }


}
