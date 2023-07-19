package com.lost.post.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.service.FileFinder;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostUpdateRequest;
import com.lost.post.controller.request.TradeRequest;
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
public class PostUpdateService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileFinder fileFinder;

    public void update(Long postId, Long userId, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("POST", postId));

        ImageCreateRequest imageCreateRequest = postUpdateRequest.getImageCreateRequest();
        if (!(Objects.isNull(imageCreateRequest) || imageCreateRequest.isEmpty())) {
            for (ImageCreate imageCreate : imageCreateRequest.getImageCreate()) {
                fileFinder.find(imageCreate.getFileName());
            }
        }

        post.update(postUpdateRequest, userId);
        postRepository.save(post);
    }

    public void trade(Long postId, TradeRequest tradeRequest, Long ownerId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("POST", postId));
        User founder = userRepository.findById(tradeRequest.getTraderId())
                .orElseThrow(() -> new ResourceNotFoundException("USER", tradeRequest.getTraderId()));

        post.tradeItem(ownerId, founder);
    }
}
