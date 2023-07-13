package com.lost.chat.controller.response;

import com.lost.chat.domain.Chat;
import com.lost.chat.domain.ChatType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatResponse {

    private final Long id;
    private final String content;
    private final ChatType type;
    private final LocalDateTime createdAt;
    private final ChatSender sender;

    @Builder
    public ChatResponse(Long id, String content, ChatType type, LocalDateTime createdAt, ChatSender sender) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.sender = sender;
    }

    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .content(chat.getContent())
                .type(chat.getType())
                .createdAt(chat.getCreatedAt())
                .sender(ChatSender.builder()
                        .id(chat.getSender().getId())
                        .nickname(chat.getSender().getNickname())
                        .build())
                .build();
    }
}
