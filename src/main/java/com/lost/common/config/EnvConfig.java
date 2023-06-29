package com.lost.common.config;

import com.lost.common.domain.EnvProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EnvProperties.class)
public class EnvConfig {

}
