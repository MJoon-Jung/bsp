package com.lost.chat.controller;

import com.lost.chat.controller.payload.ChatPayload;
import com.lost.chat.controller.response.ChatResponse;
import com.lost.chat.service.ChatService;
import com.lost.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @SendTo("/sub/chat-rooms/{roomId}")
    @MessageMapping("/chat-rooms/{roomId}")
    public ChatResponse sendMessage(@DestinationVariable Long roomId, @Payload ChatPayload chatPayload,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return chatService.save(roomId, chatPayload, userPrincipal.getUserId());
    }
}
