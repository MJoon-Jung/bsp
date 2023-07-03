package com.lost.post.controller.response;

import com.lost.image.domain.PostImage;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostImageResponse {

    private final Long id;
    private final String url;
    private final String fileName;
    private final String originalFileName;

    @Builder
    public PostImageResponse(Long id, String url, String fileName, String originalFileName) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }

    public static PostImageResponse from(PostImage postImage) {
        return PostImageResponse.builder()
                .id(postImage.getId())
                .url(postImage.getUrl())
                .fileName(postImage.getFileName())
                .originalFileName(postImage.getOriginalFileName())
                .build();
    }
}
