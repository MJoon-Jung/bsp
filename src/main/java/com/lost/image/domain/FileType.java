package com.lost.image.domain;

import java.util.Arrays;

public enum FileType {
    JPG, JPEG, PNG;

    public static FileType from(String fileExtension) {
        return Arrays.stream(values())
                .filter(fileType -> fileType.name().equalsIgnoreCase(fileExtension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일 확장자입니다."));
    }
}
