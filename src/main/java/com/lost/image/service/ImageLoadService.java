package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.image.infra.ImagePrinter;
import com.lost.image.infra.ResourceFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageLoadService {

    private final ImageConfig imageConfig;
    private final ResourceFinder resourceFinder;

    public ImagePrinter load(String imageName) {
        return ImagePrinter.builder()
                .imageName(imageName)
                .resource(resourceFinder.find(imageConfig.getResourcePath(), imageName))
                .build();
    }
}
