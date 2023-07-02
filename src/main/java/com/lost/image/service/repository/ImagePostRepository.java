package com.lost.image.service.repository;

import com.lost.image.domain.PostImage;
import java.util.List;
import java.util.Optional;

public interface ImagePostRepository {

    PostImage save(PostImage image);

    Optional<PostImage> findById(Long imageId);

    List<PostImage> saveAll(List<PostImage> postImageJpaEntities);
}
