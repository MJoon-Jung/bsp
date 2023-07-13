package com.lost.chat.controller.payload;

import com.lost.chat.domain.ChatType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatPayload {

    @NotBlank
    private String content;
    @NotBlank
    private ChatType type;

    public ChatPayload(String content, ChatType type) {
        this.content = content;
        this.type = type;
    }
}
