package com.lost;

import com.lost.common.domain.StorageProperties;
import com.lost.image.service.FileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaAuditing
@SpringBootApplication
public class LostApplication {

    public static void main(String[] args) {
        SpringApplication.run(LostApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileUploadService fileSaver) {
        return (args) -> fileSaver.init();
    }
}
