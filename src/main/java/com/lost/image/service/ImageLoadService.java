package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.StorageException;
import com.lost.image.service.dto.ImageResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ImageLoadService {

    private static final String LOAD_IMAGE_FAIL_MESSAGE = "이미지를 불러오지 못했습니다.";
    public static final String PNG = "PNG";
    private final ImageConfig imageConfig;

    public ImageResponse load(String imageName) {
        return ImageResponse.builder()
                .contentType(getContentType(imageName))
                .content(getFileContent(imageName))
                .build();
    }

    private byte[] getFileContent(String imageName) {
        File file = new File(imageConfig.getRootLocation(), imageName);
        if (!file.exists()) {
            throw new StorageException("파일이 존재하지 않습니다.");
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MediaType getContentType(String imageName) {
        String extension = StringUtils.getFilenameExtension(imageName);
        return Objects.requireNonNull(extension).equalsIgnoreCase(PNG) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
    }
}
