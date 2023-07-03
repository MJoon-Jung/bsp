package com.lost.post.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.infra.ResourceUtil;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostUpdateRequest;
import com.lost.post.domain.Post;
import com.lost.post.infra.repository.PostRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUpdateService {

    private final PostRepository postRepository;
    private final ResourceUtil resourceUtil;

    public void update(Long postId, Long userId, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("POST", postId));

        ImageCreateRequest imageCreateRequest = postUpdateRequest.getImageCreateRequest();
        if (!(Objects.isNull(imageCreateRequest) || imageCreateRequest.isEmpty())) {
            for (ImageCreate imageCreate : imageCreateRequest.getImageCreate()) {
                resourceUtil.find(imageCreate.getFileName());
            }
        }

        post.update(postUpdateRequest, userId);
        postRepository.save(post);
    }
}
