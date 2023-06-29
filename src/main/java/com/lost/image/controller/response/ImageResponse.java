package com.lost.image.controller.response;

import com.lost.image.domain.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponse {

    private Long id;
    private String url;
    private String fileName;

    @Builder
    public ImageResponse(Long id, String url, String fileName) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
    }

    public static ImageResponse from(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .fileName(image.getFileName())
                .build();
    }
}