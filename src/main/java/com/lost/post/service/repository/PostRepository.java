package com.lost.post.service.repository;

import com.lost.post.domain.Post;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long postId);
}
