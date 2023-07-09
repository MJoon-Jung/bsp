package com.lost.post.controller;

import com.lost.post.controller.request.PostsLoadCondition;
import com.lost.post.controller.response.PostResponse;
import com.lost.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> loadList(PostsLoadCondition postsLoadCondition) {
        return ResponseEntity.ok()
                .body(postService.loadList(postsLoadCondition));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> load(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.load(postId)));
    }
}
