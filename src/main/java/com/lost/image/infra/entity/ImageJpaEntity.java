package com.lost.image.infra.entity;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.image.domain.FileType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class ImageJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String fileName;
    private Integer fileSize;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
}
