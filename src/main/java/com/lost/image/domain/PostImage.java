package com.lost.image.domain;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "POST_IMAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostImage extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String fileName;
    private String originalFileName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public PostImage(Long id, String url, String fileName, String originalFileName, Post post) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.post = post;
        post.getPostImages().add(this);
    }
}
