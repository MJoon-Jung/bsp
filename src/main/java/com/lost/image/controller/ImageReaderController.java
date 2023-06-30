package com.lost.image.controller;

import com.lost.image.controller.response.ImageResponse;
import com.lost.image.service.ImageReaderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ImageReaderController {

    private final ImageReaderService imageUploadService;

    @GetMapping("/uploads/images/{imageId}")
    public ResponseEntity<List<ImageResponse>> handleFileUpload() {
        return null;
    }
}
