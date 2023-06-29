package com.lost.image.service.repository;

import com.lost.image.domain.Image;
import com.lost.image.infra.entity.ImagePostJpaEntity;
import java.util.List;
import java.util.Optional;

public interface ImagePostRepository {

    Image save(Image image);

    Optional<Image> findById(Long imageId);

    List<ImagePostJpaEntity> saveAll(List<ImagePostJpaEntity> imagePostJpaEntities);
}
