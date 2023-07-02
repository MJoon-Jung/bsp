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
public class Image {

    private Long id;
    private String url;
    private String fileName;
    private String originalFileName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Image(Long id, String url, String fileName, String originalFileName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}