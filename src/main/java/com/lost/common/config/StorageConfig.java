package com.lost.common.config;

import com.lost.common.domain.StorageProperties;
import com.lost.common.domain.StorageProperties.ImageConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {StorageProperties.class, ImageConfig.class})
public class StorageConfig {

}
