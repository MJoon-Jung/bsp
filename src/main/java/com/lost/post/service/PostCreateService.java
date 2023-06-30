package com.lost.post.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.domain.ImagePost;
import com.lost.image.service.repository.ImagePostRepository;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.domain.Post;
import com.lost.post.service.repository.PostRepository;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.List;
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

    public Post create(PostCreateRequest postCreateRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (postCreateRequest.getImages() == null) {
            return postRepository.save(Post.from(postCreateRequest, user));
        }

        List<ImagePost> images = postCreateRequest.getImages().stream()
                .map(imageId -> imagePostRepository.findById(imageId)
                        .orElseThrow(() -> new ResourceNotFoundException("IMAGE", imageId))
                ).toList();
        return postRepository.save(Post.from(postCreateRequest, user, images));
    }
}
