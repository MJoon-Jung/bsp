package com.lost.post.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.infra.ResourceFinder;
import com.lost.image.service.repository.ImagePostRepository;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.service.repository.PostRepository;
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
    private final ImagePostRepository imagePostRepository;
    private final UserRepository userRepository;
    private final ImageConfig imageConfig;

    public Post create(Long userId, PostCreateRequest postCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        ImageCreateRequest imageCreateRequest = postCreateRequest.getImageCreateRequest();
        if (Objects.isNull(imageCreateRequest) || imageCreateRequest.isEmpty()) {
            return postRepository.save(Post.from(postCreateRequest, user));
        }

        for (ImageCreate imageCreate : imageCreateRequest.getImageCreate()) {
            ResourceFinder resourceFinder = new ResourceFinder();
            resourceFinder.find(imageConfig.getResourcePath(), imageCreate.getFileName());
        }

        return postRepository.save(Post.from(postCreateRequest, user, imageCreateRequest));
    }
}
