package com.lost.chat.controller;

import com.lost.chat.service.ChatRoomCreateService;
import com.lost.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomCreateController {

    private final ChatRoomCreateService chatRoomCreateService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/posts/{postId}/chat-rooms")
    public void create(@PathVariable Long postId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        chatRoomCreateService.create(postId, userPrincipal.getUserId());
    }
}
