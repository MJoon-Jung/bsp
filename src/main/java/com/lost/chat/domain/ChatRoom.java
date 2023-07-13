package com.lost.chat.domain;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.post.domain.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomMember> members = new ArrayList<>();
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;
    @OneToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "LAST_CHAT_ID")
    private Chat lastChat;

    @Builder
    public ChatRoom(Long id, List<ChatRoomMember> members, List<Chat> chats, Post post, Chat lastChat) {
        this.id = id;
        this.members = members == null ? this.members : members;
        this.chats = chats == null ? this.chats : chats;
        this.post = post;
        this.lastChat = lastChat;
    }

    public ChatRoomMember findMemberByUserId(Long userId) {
        return getMembers().stream()
                .filter(chatRoomMember -> chatRoomMember.getMember().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CHAT_ROOM_MEMBER", "USER_ID", userId));
    }

    public ChatRoomMember findOtherMemberByUserId(Long userId) {
        return getMembers().stream()
                .filter(chatRoomMember -> !chatRoomMember.getMember().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CHAT_ROOM_MEMBER", "USER_ID", userId));
    }

    public static ChatRoom newChatRoom(Post post) {
        return ChatRoom.builder()
                .post(post)
                .build();
    }
}