package com.lost.image.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Image {

    private final Long id;
    private final String url;
    private final String fileName;
    private final Integer fileSize;
    private final FileType fileType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public Image(Long id, String url, String fileName, Integer fileSize, FileType fileType, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
