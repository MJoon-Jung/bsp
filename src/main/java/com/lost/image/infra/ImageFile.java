package com.lost.image.infra;

import com.lost.common.domain.exception.StorageException;
import com.lost.common.service.UuidGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ImageFile {

    private static final String DELIMITER = "_";
    private static final String URL_FORMAT = "%s/%s";
    private final MultipartFile image;
    private final String imageName;
    private final String baseUrl;

    @Builder
    public ImageFile(MultipartFile image, String imageName, String baseUrl) {
        validate(image);
        this.image = image;
        this.imageName = imageName;
        this.baseUrl = baseUrl;
    }

    private static void validate(MultipartFile image) {
        if (image.isEmpty()) {
            throw new StorageException("빈 파일을 저장할 수 없습니다.");
        }
    }

    public void save(String rootLocation) {
        Path destinationFile = Paths.get(rootLocation, imageName)
                .normalize()
                .toAbsolutePath();
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("image file save error = {}", e.getMessage());
            e.printStackTrace();
            throw new StorageException("이미지 저장에 실패했습니다.", e);
        }
    }

    public String getImageName() {
        return imageName;
    }

    public String getOriginalFileName() {
        return image.getOriginalFilename();
    }

    public String getImageUrl() {
        return String.format(URL_FORMAT, baseUrl, imageName);
    }

    public static ImageFile from(MultipartFile image, UuidGenerator uuidGenerator, String baseUrl) {
        return ImageFile.builder()
                .image(image)
                .imageName(uuidGenerator.generate() + DELIMITER + image.getOriginalFilename())
                .baseUrl(baseUrl)
                .build();
    }
}
