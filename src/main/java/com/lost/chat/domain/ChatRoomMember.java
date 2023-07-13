package com.lost.chat.domain;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMember extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User member;
    @Column(columnDefinition = "boolean")
    @ColumnDefault("true")
    @Getter(AccessLevel.NONE)
    private Boolean active;

    @Builder
    public ChatRoomMember(Long id, ChatRoom chatRoom, User member, Boolean active) {
        this.id = id;
        this.chatRoom = chatRoom;
        chatRoom.getMembers().add(this);
        this.member = member;
        this.active = active;
    }

    public void joinRoom() {
        this.active = true;
    }

    public void exitRoom() {
        this.active = false;
    }

    public Boolean isActive() {
        return this.active;
    }
}
