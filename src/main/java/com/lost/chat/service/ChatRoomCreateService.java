package com.lost.chat.service;

import com.lost.chat.domain.ChatRoom;
import com.lost.chat.domain.ChatRoomMember;
import com.lost.chat.infra.repository.ChatRoomQueryRepository;
import com.lost.chat.infra.repository.ChatRoomRepository;
import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.post.domain.Post;
import com.lost.post.infra.repository.PostRepository;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomCreateService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomQueryRepository chatRoomQueryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void create(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("POST", postId));

        User writer = post.getUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER", userId));

        Optional<ChatRoom> maybeChatRoom = chatRoomQueryRepository.findByMembers(post.getId(), writer.getId(),
                user.getId());
        if (maybeChatRoom.isPresent()) {
            ChatRoomMember roomMember = maybeChatRoom.get().findMemberByUserId(userId);
            // 이미 채팅방이 존재하지만 채팅방을 나간 경우 다시 참여시킨다.
            if (!roomMember.isActive()) {
                roomMember.joinRoom();
            }
            return;
        }

        ChatRoom chatRoom = ChatRoom.newChatRoom(post);
        ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(user)
                .active(true)
                .build();
        ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(writer)
                .active(true)
                .build();

        chatRoomRepository.save(chatRoom);
    }
}
