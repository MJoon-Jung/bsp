package com.lost.image.service.repository;

import com.lost.image.domain.ImagePost;
import com.lost.image.infra.entity.ImagePostJpaEntity;
import java.util.List;
import java.util.Optional;

public interface ImagePostRepository {

    ImagePost save(ImagePost image);

    Optional<ImagePost> findById(Long imageId);

    List<ImagePostJpaEntity> saveAll(List<ImagePostJpaEntity> imagePostJpaEntities);
}
