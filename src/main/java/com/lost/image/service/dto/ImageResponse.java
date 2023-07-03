package com.lost.image.service.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public class ImageResponse {
    private final MediaType contentType;
    private final byte[] content;

    @Builder
    public ImageResponse(MediaType contentType, byte[] content) {
        this.contentType = contentType;
        this.content = content;
    }
}
