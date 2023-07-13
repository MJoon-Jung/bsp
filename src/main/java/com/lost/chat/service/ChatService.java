package com.lost.chat.service;

import com.lost.chat.controller.payload.ChatPayload;
import com.lost.chat.controller.response.ChatResponse;
import com.lost.chat.domain.Chat;
import com.lost.chat.domain.ChatRoom;
import com.lost.chat.domain.ChatRoomMember;
import com.lost.chat.infra.repository.ChatRepository;
import com.lost.chat.infra.repository.ChatRoomRepository;
import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatResponse save(Long roomId, ChatPayload chatPayload, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("CHAT_ROOM", roomId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER", userId));

        chatRoom.getMembers().stream()
                .filter(member -> !member.isActive())
                .forEach(ChatRoomMember::joinRoom);

        Chat chat = Chat.builder()
                .content(chatPayload.getContent())
                .sender(user)
                .type(chatPayload.getType())
                .room(chatRoom)
                .build();
        chat = chatRepository.save(chat);
        return ChatResponse.from(chat);
    }
}
