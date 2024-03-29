package com.lost.post.controller;

import com.lost.post.controller.request.PostUpdateRequest;
import com.lost.post.controller.request.TradeRequest;
import com.lost.post.controller.response.PostResponse;
import com.lost.post.service.PostService;
import com.lost.post.service.PostUpdateService;
import com.lost.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostUpdateController {

    private final PostUpdateService postUpdateService;
    private final PostService postService;

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> update(@PathVariable Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postUpdateService.update(postId, userPrincipal.getUserId(), postUpdateRequest);
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.load(postId)));
    }

    @PutMapping("/{postId}/trade")
    public void trade(@PathVariable Long postId,
            @RequestBody TradeRequest tradeRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postUpdateService.trade(postId, tradeRequest, userPrincipal.getUserId());
    }
}
