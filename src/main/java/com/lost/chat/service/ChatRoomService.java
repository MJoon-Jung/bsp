package com.lost.chat.service;

import com.lost.chat.controller.response.ChatResponse;
import com.lost.chat.controller.response.SimpleChatRoomResponse;
import com.lost.chat.domain.Chat;
import com.lost.chat.domain.ChatRoom;
import com.lost.chat.domain.ChatRoomMember;
import com.lost.chat.infra.repository.ChatRoomQueryRepository;
import com.lost.chat.infra.repository.ChatRoomRepository;
import com.lost.common.domain.exception.ForbiddenException;
import com.lost.common.domain.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomQueryRepository chatRoomCustomRepository;

    @Transactional(readOnly = true)
    public List<SimpleChatRoomResponse> loadList(Long userId) {
        List<ChatRoom> chatRooms = chatRoomCustomRepository.findByUserId(userId);
        return chatRooms.stream()
                .map(chatRoom -> SimpleChatRoomResponse.from(chatRoom, userId))
                .toList();
    }

    public void delete(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("CHAT_ROOM", chatRoomId));

        ChatRoomMember me = chatRoom.findMemberByUserId(userId);
        ChatRoomMember other = chatRoom.findOtherMemberByUserId(userId);

        //상대방이 이미 나갔으면 방을 삭제하지만, 상대방이 나가지 않았으면 나간다.
        if (!other.isActive()) {
            chatRoomRepository.delete(chatRoom);
            return;
        }
        me.exitRoom();
    }

    public List<ChatResponse> loadChats(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("CHAT_ROOM", chatRoomId));
        ChatRoomMember roomMember = chatRoom.findMemberByUserId(userId);
        // 채팅방을 나갔을 때
        if (!roomMember.isActive()) {
            throw new ForbiddenException();
        }

        List<Chat> chats = chatRoomCustomRepository.findChats(chatRoomId, userId);
        return chats.stream()
                .map(ChatResponse::from)
                .toList();
    }
}
