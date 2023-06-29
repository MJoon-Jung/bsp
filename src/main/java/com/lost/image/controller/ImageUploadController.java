package com.lost.image.controller;

import com.lost.image.controller.response.ImageResponse;
import com.lost.image.domain.Image;
import com.lost.image.service.ImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageService imageService;

    @PostMapping("/api/posts/images")
    public ResponseEntity<List<ImageResponse>> handleFileUpload(
            @RequestParam("image") List<MultipartFile> imageFiles) {
        List<Image> images = imageService.store(imageFiles);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(images.stream().map(ImageResponse::from).toList());
    }
}
