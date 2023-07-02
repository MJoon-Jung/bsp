package com.lost.image.infra.repository;

import com.lost.image.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagePostJpaRepository extends JpaRepository<PostImage, Long> {

}
