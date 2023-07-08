package com.lost.post.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.post.controller.request.MapArea;
import com.lost.post.controller.request.PostsLoadCondition;
import com.lost.post.controller.response.PostResponse;
import com.lost.post.domain.Post;
import com.lost.post.infra.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post load(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("POST", postId));
    }

    public List<PostResponse> loadList(PostsLoadCondition postsLoadCondition) {
        MapArea mapArea = new MapArea(postsLoadCondition);
        List<Post> posts = postRepository.findAllByPolygon(
                mapArea.getSoutheast(),
                mapArea.getSouthwest(),
                mapArea.getNortheast(),
                mapArea.getNorthwest());

        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }
}