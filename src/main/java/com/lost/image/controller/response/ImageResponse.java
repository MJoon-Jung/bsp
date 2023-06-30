package com.lost.image.controller.response;

import com.lost.image.domain.ImagePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponse {

    private final String url;
    private final String fileName;
    private final String originalFileName;

    @Builder
    public ImageResponse(String url, String fileName, String originalFileName) {
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }

    public static ImageResponse from(ImagePost image) {
        return ImageResponse.builder()
                .url(image.getUrl())
                .fileName(image.getFileName())
                .originalFileName(image.getOriginalFileName())
                .build();
    }
}