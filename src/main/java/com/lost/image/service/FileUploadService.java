package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.StorageException;
import com.lost.image.service.dto.FileSaveResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final ImageConfig imageConfig;
    private final FileGenerator fileGenerator;
    private final FileGeneratorUtil fileGeneratorUtil;

    public List<FileSaveResult> store(List<MultipartFile> images) {
        return images.stream().map(this::store).toList();
    }

    public FileSaveResult store(MultipartFile file) {
        validateFile(file);
        String fileName = fileGeneratorUtil.generateFileName(file.getOriginalFilename());
        Path absolutePath = fileGeneratorUtil.generateAbsolutePath(fileName);

        fileGenerator.save(file, absolutePath);
        return FileSaveResult.builder()
                .url(fileGeneratorUtil.generateFileUrl(imageConfig.getBaseUrl(), fileName))
                .fileName(fileName)
                .originalFileName(file.getOriginalFilename())
                .build();
    }

    private static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("빈 파일을 저장할 수 없습니다.");
        }
    }

    public void init() {
        try {
            Files.createDirectories(Path.of(imageConfig.getRootLocation()));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
