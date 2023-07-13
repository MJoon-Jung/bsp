package com.lost.chat.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lost.chat.domain.Chat;
import com.lost.chat.domain.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SqlGroup({
        @Sql(value = "/sql/insert-chat-repository-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
class ChatRoomQueryRepositoryTest {

    @Autowired
    private ChatRoomQueryRepository chatRoomQueryRepository;


    @Test
    @DisplayName("특정 게시글에 관한 두 유저 간의 채팅방이 존재하지 않으면 빈 Optional을 반환한다.")
    void should_return_an_empty_optional_when_no_chat_room_exist() {
        //given
        //when
        Optional<ChatRoom> maybeChatRoom = chatRoomQueryRepository.findByMembers(3L, 2L, 1L);
        //then
        assertThat(maybeChatRoom).isNotPresent();
    }

    @Test
    @DisplayName("특정 게시글에 관한 두 유저 간의 채팅방이 존재하면 값이 있는 Optional을 반환한다.")
    void should_return_an_optional_when_chat_room_exist() {
        //given
        //when
        Optional<ChatRoom> maybeChatRoom = chatRoomQueryRepository.findByMembers(1L, 1L, 2L);
        //then
        assertThat(maybeChatRoom).isPresent();
    }

    @Test
    @DisplayName("유저가 속한 채팅방을 불러온다.")
    void should_return_the_chat_rooms_the_user_joined() {
        //given
        //when
        List<ChatRoom> chatRooms = chatRoomQueryRepository.findByUserId(1L);
        //then
        assertThat(chatRooms.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저가 속한 채팅방이 없으면 빈 채팅방 목록을 반환한다.")
    void should_return_an_empty_chat_rooms() {
        //given
        //when
        List<ChatRoom> chatRooms = chatRoomQueryRepository.findByUserId(3L);
        //then
        assertThat(chatRooms.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("특정 채팅방의 채팅 목록을 불러온다.")
    void should_return_chat_list() {
        //given
        //when
        List<Chat> chats = chatRoomQueryRepository.findChats(2L, 2L);
        //then
        assertThat(chats.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("특정 채팅방의 채팅 목록을 불러온다.")
    void should_return_chat_list2() {
        //given
        //when
        List<Chat> chats = chatRoomQueryRepository.findChats(2L, 1L);
        //then
        assertThat(chats.size()).isEqualTo(6);
    }
}