package com.lost.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("storage")
public class StorageProperties {

    private final ImageConfig imageConfig;

    @Getter
    @RequiredArgsConstructor
    @ConfigurationProperties("storage.image-config")
    public static final class ImageConfig {

        private final String rootLocation;
        private final String baseUrl;
        private final String resourcePath;
    }
}
