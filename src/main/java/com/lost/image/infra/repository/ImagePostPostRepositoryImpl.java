package com.lost.image.infra.repository;

import com.lost.image.domain.PostImage;
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
    public Image save(Image image) {
        return imagePostJpaRepository.save(PostImage.from(image, null)).toModel();
    }

    @Override
    public PostImage save(PostImage image) {
        return null;
    }

    @Override
    public Optional<Image> findById(Long imageId) {
        return imagePostJpaRepository.findById(imageId).map(PostImage::toModel);
    }

    @Override
    public List<PostImage> saveAll(List<PostImage> postImageJpaEntities) {
        return imagePostJpaRepository.saveAll(postImageJpaEntities);
    }
}
