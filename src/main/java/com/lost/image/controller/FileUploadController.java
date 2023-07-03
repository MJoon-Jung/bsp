package com.lost.image.controller;

import com.lost.image.service.FileUploadService;
import com.lost.image.service.dto.FileSaveResult;
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
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/api/posts/images")
    public ResponseEntity<List<FileSaveResult>> handleFileUpload(
            @RequestParam("image") List<MultipartFile> imageFiles) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fileUploadService.store(imageFiles));
    }
}
