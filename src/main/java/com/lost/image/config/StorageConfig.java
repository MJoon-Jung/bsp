package com.lost.image.config;

import com.lost.image.domain.StorageProperties;
import com.lost.image.domain.StorageProperties.ImageConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {StorageProperties.class, ImageConfig.class})
public class StorageConfig {

}
