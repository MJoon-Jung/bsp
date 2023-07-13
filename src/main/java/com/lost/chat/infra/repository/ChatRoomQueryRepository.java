package com.lost.chat.infra.repository;

import static com.lost.chat.domain.QChat.chat;
import static com.lost.chat.domain.QChatRoom.chatRoom;
import static com.lost.chat.domain.QChatRoomMember.chatRoomMember;
import static com.lost.post.domain.QPost.post;
import static com.lost.user.domain.QUser.user;
import static com.querydsl.jpa.JPAExpressions.select;

import com.lost.chat.domain.Chat;
import com.lost.chat.domain.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomQueryRepository {

    private final JPAQueryFactory query;

    public Optional<ChatRoom> findByMembers(Long postId, Long userId, Long userId2) {
        ChatRoom result = query.select(chatRoom)
                .from(chatRoom)
                .join(chatRoom.members, chatRoomMember)
                .where(chatRoomMember.member.id.eq(userId)
                        .and(chatRoom.id.eq(
                                select(chatRoom.id)
                                        .from(chatRoom)
                                        .join(chatRoom.members, chatRoomMember)
                                        .join(chatRoom.post, post)
                                        .where(chatRoomMember.member.id.eq(userId2)
                                                .and(post.id.eq(postId))))))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    public List<ChatRoom> findByUserId(Long userId) {
        return query.selectFrom(chatRoom)
                .join(chatRoom.members, chatRoomMember)
                .where(chatRoomMember.member.id.eq(userId)
                        .and(chatRoomMember.active.isTrue()))
                .fetch();
    }

    public List<Chat> findChats(Long chatRoomId, Long userId) {
        return query.selectFrom(chat)
                .join(chat.room, chatRoom)
                .where(chatRoom.id.eq(chatRoomId)
                        .and(chat.createdAt.after(
                                select(chatRoomMember.updatedAt)
                                        .from(chatRoomMember)
                                        .join(chatRoomMember.member, user)
                                        .join(chatRoomMember.chatRoom, chatRoom)
                                        .where(chatRoom.id.eq(chatRoomId)
                                                .and(user.id.eq(userId))))))
                .orderBy(chat.id.desc())
                .fetch();
    }
}
