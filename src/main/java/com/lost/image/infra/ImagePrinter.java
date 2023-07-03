package com.lost.image.infra;

import com.lost.common.domain.exception.StorageException;
import com.lost.image.service.dto.ImageResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImagePrinter {

    private static final String LOAD_IMAGE_FAIL_MESSAGE = "이미지를 불러오지 못했습니다.";
    public static final String PNG = "PNG";
    private final ResourceUtil resourceUtil;

    public ImageResponse print(String imageName) {
        return ImageResponse.builder()
                .contentType(getContentType(imageName))
                .content(getContent(resourceUtil.find(imageName), imageName))
                .build();
    }

    private byte[] getContent(Resource resource, String imageName) {
        try {
            return resource.getContentAsByteArray();
        } catch (IOException e) {
            log.error("image load fail = {}", imageName);
            throw new StorageException(LOAD_IMAGE_FAIL_MESSAGE);
        }
    }

    private MediaType getContentType(String imageName) {
        String extension = StringUtils.getFilenameExtension(imageName);
        return Objects.requireNonNull(extension).equalsIgnoreCase(PNG) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
    }
}
