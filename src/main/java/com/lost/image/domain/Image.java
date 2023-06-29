package com.lost.image.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {

    private Long id;
    private String url;
    private String fileName;
    private Long fileSize;
    private FileType fileType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Image(Long id, String url, String fileName, Long fileSize, FileType fileType, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Image from(MultipartFile imageFile, String url) {
        String filenameExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        FileType fileType = FileType.valueOf(filenameExtension);
        System.out.println("filenameExtension = " + filenameExtension);
        System.out.println("fileType = " + fileType);

        return Image.builder()
                .url(url)
                .fileName(imageFile.getOriginalFilename())
                .fileSize(imageFile.getSize())
                .fileType(fileType)
                .build();
    }
}