package com.lost.image.service;

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

    private static final String PNG = "PNG";
    private final FileFinder fileFinder;

    public ImageResponse load(String imageName) {
        return ImageResponse.builder()
                .contentType(getContentType(imageName))
                .content(getFileContent(imageName))
                .build();
    }

    private byte[] getFileContent(String imageName) {
        File file = fileFinder.find(imageName);
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
