package com.lost.image.infra.repository;

import com.lost.image.domain.ImagePost;
import com.lost.image.infra.entity.ImagePostJpaEntity;
import com.lost.image.service.repository.ImagePostRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImagePostPostRepositoryImpl implements ImagePostRepository {

    private final ImagePostJpaRepository imagePostJpaRepository;

    @Override
    public ImagePost save(ImagePost image) {
        return imagePostJpaRepository.save(ImagePostJpaEntity.from(image, null)).toModel();
    }

    @Override
    public Optional<ImagePost> findById(Long imageId) {
        return imagePostJpaRepository.findById(imageId).map(ImagePostJpaEntity::toModel);
    }

    @Override
    public List<ImagePostJpaEntity> saveAll(List<ImagePostJpaEntity> imagePostJpaEntities) {
        return imagePostJpaRepository.saveAll(imagePostJpaEntities);
    }
}
