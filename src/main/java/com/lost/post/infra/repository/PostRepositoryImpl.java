package com.lost.post.infra.repository;

import com.lost.image.service.repository.ImagePostRepository;
import com.lost.post.domain.Post;
import com.lost.post.service.repository.PostRepository;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final ImagePostRepository imagePostRepository;
    private final UserRepository userRepository;

    @Override
    public Post save(Post post) {
        Post entity = postJpaRepository.save(Post.from(post));
        imagePostRepository.saveAll(entity.getPostImageJpaEntities());
        userRepository.save(entity.getUser());
        return entity.toModel();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postJpaRepository.findById(postId).map(Post::toModel);
    }
}
