package com.lost.chat.controller.response;

import com.lost.chat.domain.ChatRoom;
import com.lost.chat.domain.ChatRoomMember;
import com.lost.common.domain.exception.MemberNotFoundException;
import com.lost.post.controller.response.PostResponse;
import com.lost.user.controller.response.SimpleUserInfoResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleChatRoomResponse {

    private final Long chatRoomId;
    private final SimpleUserInfoResponse member;
    private final PostResponse post;
    private final ChatResponse lastChat;

    @Builder
    public SimpleChatRoomResponse(Long chatRoomId, SimpleUserInfoResponse member, PostResponse post,
            ChatResponse lastChat) {
        this.chatRoomId = chatRoomId;
        this.member = member;
        this.post = post;
        this.lastChat = lastChat;
    }

    public static SimpleChatRoomResponse from(ChatRoom chatRoom, Long userId) {
        ChatRoomMember roomMember = chatRoom.getMembers().stream()
                .filter(chatRoomMember -> !chatRoomMember.getMember().getId().equals(userId))
                .findFirst()
                .orElseThrow(MemberNotFoundException::new);

        return SimpleChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .post(PostResponse.from(chatRoom.getPost()))
                .lastChat(chatRoom.getLastChat() == null ? null : ChatResponse.from(chatRoom.getLastChat()))
                .member(SimpleUserInfoResponse.from(roomMember.getMember()))
                .build();
    }
}
