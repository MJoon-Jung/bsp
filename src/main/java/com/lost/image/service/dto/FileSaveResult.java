package com.lost.image.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileSaveResult {

    private final String url;
    private final String fileName;
    private final String originalFileName;

    @Builder
    public FileSaveResult(String url, String fileName, String originalFileName) {
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }
}
