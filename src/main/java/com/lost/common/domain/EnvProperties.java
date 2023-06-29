package com.lost.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("env")
public class EnvProperties {

    private final String clientUrl;
}
