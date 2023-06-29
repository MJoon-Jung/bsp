package com.lost.image.infra.repository;

import com.lost.image.domain.Image;
import com.lost.image.infra.entity.ImagePostJpaEntity;
import com.lost.image.service.repository.ImagePostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImagePostPostRepositoryImpl implements ImagePostRepository {

    private final ImagePostJpaRepository imagePostJpaRepository;

    @Override
    public Image save(Image image) {
        return imagePostJpaRepository.save(ImagePostJpaEntity.from(image, null)).toModel();
    }

    @Override
    public Optional<Image> findById(Long imageId) {
        return imagePostJpaRepository.findById(imageId).map(ImagePostJpaEntity::toModel);
    }
}
