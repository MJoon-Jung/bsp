package com.lost.image.infra;

import com.lost.common.domain.exception.StorageException;
import java.io.IOException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Slf4j
public class ImagePrinter {

    private static final DefaultResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
    private static final String LOAD_IMAGE_FAIL_MESSAGE = "이미지를 불러오지 못했습니다.";
    private final String imageName;
    private final Resource resource;

    @Builder
    public ImagePrinter(String imageName, Resource resource) {
        this.imageName = imageName;
        this.resource = resource;
    }

    public byte[] getContent() {
        try {
            return resource.getContentAsByteArray();
        } catch (IOException e) {
            log.error("image load fail = {}", imageName);
            throw new StorageException(LOAD_IMAGE_FAIL_MESSAGE);
        }
    }

    public MediaType getContentType() {
        String extension = imageName.split("\\.")[1].toLowerCase();
        return extension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
    }
}
