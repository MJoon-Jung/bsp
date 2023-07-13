package com.lost.chat.controller;

import com.lost.chat.controller.response.ChatResponse;
import com.lost.chat.controller.response.SimpleChatRoomResponse;
import com.lost.chat.service.ChatRoomService;
import com.lost.security.userdetails.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/api/chat-rooms")
    public ResponseEntity<List<SimpleChatRoomResponse>> loadList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok()
                .body(chatRoomService.loadList(userPrincipal.getUserId()));
    }

    @GetMapping("/api/chat-rooms/{chatRoomId}/chats")
    public ResponseEntity<List<ChatResponse>> loadChats(@PathVariable Long chatRoomId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok()
                .body(chatRoomService.loadChats(chatRoomId, userPrincipal.getUserId()));
    }

    @DeleteMapping("/api/chat-rooms/{chatRoomId}")
    public void delete(@PathVariable Long chatRoomId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        chatRoomService.delete(chatRoomId, userPrincipal.getUserId());
    }
}
