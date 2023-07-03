package com.lost.post.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.infra.ResourceUtil;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.controller.response.PostResponse;
import com.lost.post.domain.Post;
import com.lost.post.infra.repository.PostRepository;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCreateService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ResourceUtil resourceUtil;

    public PostResponse create(Long userId, PostCreateRequest postCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Post post = postCreateRequest.toEntity(user);

        ImageCreateRequest imageCreateRequest = postCreateRequest.getImageCreateRequest();
        if (!(Objects.isNull(imageCreateRequest) || imageCreateRequest.isEmpty())) {
            for (ImageCreate imageCreate : imageCreateRequest.getImageCreate()) {
                resourceUtil.find(imageCreate.getFileName());
            }
            imageCreateRequest.toEntity(post);
        }

        post = postRepository.save(post);
        return PostResponse.from(post);
    }
}
