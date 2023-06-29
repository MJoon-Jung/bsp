package com.lost.image.infra.repository;

import com.lost.image.infra.entity.ImagePostJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagePostJpaRepository extends JpaRepository<ImagePostJpaEntity, Long> {

}
