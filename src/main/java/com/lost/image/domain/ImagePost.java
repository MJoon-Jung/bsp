package com.lost.image.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImagePost {

    private Long id;
    private String url;
    private String fileName;
    private String originalFileName;
    private Long fileSize;
    private FileType fileType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ImagePost(Long id, String url, String fileName, String originalFileName, Long fileSize, FileType fileType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}