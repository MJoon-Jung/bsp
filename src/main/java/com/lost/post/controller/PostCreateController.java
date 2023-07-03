package com.lost.post.controller;

import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.controller.response.PostResponse;
import com.lost.post.service.PostCreateService;
import com.lost.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostCreateController {

    private final PostCreateService postCreateService;

    @PostMapping("/api/posts")
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest postCreateRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postCreateService.create(userPrincipal.getUserId(), postCreateRequest));
    }
}
