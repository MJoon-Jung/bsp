package com.lost.chat.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatSender {

    private final Long id;
    private final String nickname;

    @Builder
    public ChatSender(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
