package com.lost.image.service;

import com.lost.common.service.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileNameGenerator {

    private static final String DELIMITER = "_";
    private final UuidGenerator uuidGenerator;

    public String generate(String fileName) {
        return uuidGenerator.generate() + DELIMITER + fileName;
    }
}
