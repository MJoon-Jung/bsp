package com.lost.image.infra;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.StorageException;
import com.lost.common.service.UuidGenerator;
import com.lost.image.service.dto.FileSaveResult;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSaver {

    private static final String DELIMITER = "_";
    private static final String URL_FORMAT = "%s/%s";
    private final ImageConfig imageConfig;
    private final UuidGenerator uuidGenerator;

    public FileSaveResult save(MultipartFile file) {
        validate(file);

        String fileName = generateFileName(file.getOriginalFilename());
        Path destinationFile = Paths.get(imageConfig.getRootLocation(), fileName)
                .normalize()
                .toAbsolutePath();
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return FileSaveResult.builder()
                    .url(getUrl(fileName))
                    .fileName(fileName)
                    .originalFileName(file.getOriginalFilename())
                    .build();
        } catch (IOException e) {
            log.error("file save error = {}", e.getMessage());
            e.printStackTrace();
            throw new StorageException("파일 저장에 실패했습니다.", e);
        }
    }


    private void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("빈 파일을 저장할 수 없습니다.");
        }
    }

    private String generateFileName(String fileName) {
        return uuidGenerator.generate() + DELIMITER + fileName;
    }

    private String getUrl(String fileName) {
        return String.format(URL_FORMAT, imageConfig.getBaseUrl(), fileName);
    }
}
