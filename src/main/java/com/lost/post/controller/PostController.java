package com.lost.post.controller;

import com.lost.post.controller.response.PostResponse;
import com.lost.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> load(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.load(postId)));
    }
}
