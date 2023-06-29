package com.lost.image.service.repository;

import com.lost.image.domain.Image;
import java.util.Optional;

public interface ImagePostRepository {

    Image save(Image image);

    Optional<Image> findById(Long imageId);
}
