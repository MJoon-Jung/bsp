package com.lost.image.controller;

import com.lost.image.service.ImageLoadService;
import com.lost.image.service.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ImageLoadController {

    private final ImageLoadService imageLoadService;

    @GetMapping("/uploads/images/{imageName}")
    public ResponseEntity<byte[]> load(@PathVariable String imageName) {
        ImageResponse image = imageLoadService.load(imageName);
        return ResponseEntity.ok()
                .contentType(image.getContentType())
                .body(image.getContent());
    }
}
