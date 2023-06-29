package com.lost;

import com.lost.image.domain.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaAuditing
@SpringBootApplication
public class LostApplication {

    public static void main(String[] args) {
        SpringApplication.run(LostApplication.class, args);
    }
}
