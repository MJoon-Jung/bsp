package com.lost.image.infra.entity;

import com.lost.image.domain.ImagePost;
import com.lost.post.infra.entity.PostJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "IMAGE_POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ImagePostJpaEntity extends ImageJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private PostJpaEntity postJpaEntity;

    public ImagePost toModel() {
        return ImagePost.builder()
                .id(getId())
                .url(getUrl())
                .fileName(getFileName())
                .originalFileName(getOriginalFileName())
                .fileSize(getFileSize())
                .fileType(getFileType())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static ImagePostJpaEntity from(ImagePost image, PostJpaEntity postJpaEntity) {
        ImagePostJpaEntity imagePostJpaEntity = new ImagePostJpaEntity();
        imagePostJpaEntity.setId(image.getId());
        imagePostJpaEntity.setUrl(image.getUrl());
        imagePostJpaEntity.setFileName(image.getFileName());
        imagePostJpaEntity.setOriginalFileName(image.getOriginalFileName());
        imagePostJpaEntity.setFileSize(image.getFileSize());
        imagePostJpaEntity.setFileType(image.getFileType());
        imagePostJpaEntity.setCreatedAt(image.getCreatedAt());
        imagePostJpaEntity.setUpdatedAt(image.getUpdatedAt());
        imagePostJpaEntity.postJpaEntity = postJpaEntity;
        return imagePostJpaEntity;
    }
}
