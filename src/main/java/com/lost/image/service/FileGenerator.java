package com.lost.image.service;

import com.lost.common.domain.exception.StorageException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileGenerator {

    public void save(MultipartFile file, Path destinationFile) {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("file save error = {}", e.getMessage());
            throw new StorageException("파일 저장에 실패했습니다.", e);
        }
    }
}
