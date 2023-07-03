package com.lost.image.infra;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.common.domain.exception.StorageException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceUtil {

    private static final DefaultResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
    private static final String LOAD_IMAGE_FAIL_MESSAGE = "이미지를 불러오지 못했습니다.";
    private final ImageConfig imageConfig;

    public Resource find(String imageName) {
        return findImageByName(loadResources(imageConfig.getResourcePath()), imageName);
    }
    private Resource[] loadResources(String path) {
        try {
            return ResourcePatternUtils
                    .getResourcePatternResolver(RESOURCE_LOADER)
                    .getResources(path);
        } catch (IOException e) {
            log.error("resource directory not found. path = {}", path);
            throw new StorageException(LOAD_IMAGE_FAIL_MESSAGE);
        }
    }

    private Resource findImageByName(Resource[] resources, String imageName) {
        return Arrays.stream(resources)
                .filter(resource -> Objects.equals(resource.getFilename(), imageName))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("image not found = {}", imageName);
                    return new ResourceNotFoundException("IMAGE", "fileName", imageName);
                });
    }
}
