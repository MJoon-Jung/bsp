package com.lost.post.infra.repository;

import com.lost.image.infra.repository.ImagePostJpaRepository;
import com.lost.post.domain.Post;
import com.lost.post.infra.entity.PostJpaEntity;
import com.lost.post.service.repository.PostRepository;
import com.lost.user.infra.repository.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final ImagePostJpaRepository imagePostJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Post save(Post post) {
        PostJpaEntity entity = postJpaRepository.save(PostJpaEntity.from(post));
        imagePostJpaRepository.saveAll(entity.getImagePostJpaEntities());
        userJpaRepository.save(entity.getUserJpaEntity());
        return entity.toModel();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postJpaRepository.findById(postId).map(PostJpaEntity::toModel);
    }
}
