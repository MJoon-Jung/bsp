package com.lost.post.controller.request;

import com.lost.image.domain.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageCreate {

    @NotBlank(message = "이미지 URL은 필수 입력 값입니다.")
    private String url;
    @NotBlank(message = "이미지 파일명은 필수 입력 값입니다.")
    private String fileName;
    @NotBlank(message = "이미지 원본 파일명은 필수 입력 값입니다.")
    private String originalFileName;

    @Builder
    public ImageCreate(String url, String fileName, String originalFileName) {
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }

    public Image toImage() {
        return Image.builder()
                .url(url)
                .fileName(fileName)
                .originalFileName(originalFileName)
                .build();
    }
}
