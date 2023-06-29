package com.lost.post.infra.repository;

import com.lost.post.infra.entity.PostJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {

}
