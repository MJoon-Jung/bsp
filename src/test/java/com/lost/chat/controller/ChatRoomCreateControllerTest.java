package com.lost.chat.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lost.chat.domain.ChatRoom;
import com.lost.chat.domain.ChatRoomMember;
import com.lost.chat.infra.repository.ChatRoomQueryRepository;
import com.lost.chat.infra.repository.ChatRoomRepository;
import com.lost.security.userdetails.UserPrincipal;
import com.lost.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SqlGroup({
        @Sql(value = "/sql/insert-chat-controller-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
class ChatRoomCreateControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomQueryRepository chatRoomQueryRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("채팅방 생성 요청 성공")
    @Transactional
    void should_succeed_when_requesting_to_create_chat_room() throws Exception {
        User user = User.builder()
                .id(3L)
                .email("example3@email.com")
                .password("example3password")
                .build();

        mockMvc.perform(post("/api/posts/1/chat-rooms")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isCreated());

        ChatRoom chatRoom = chatRoomRepository.findAll(Sort.by(Direction.DESC, "id")).get(0);
        assertThat(chatRoom.getMembers().size()).isEqualTo(2);

        ChatRoomMember member1 = chatRoom.findMemberByUserId(3L);
        ChatRoomMember member2 = chatRoom.findOtherMemberByUserId(3L);

        assertThat(member1.getMember().getId()).isEqualTo(3L);
        assertThat(member2.getMember().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 채팅방이 존재하지만 나간 상태일 때 요청해도 성공")
    @Transactional
    void should_succeed_when_requesting_to_create_chat_room_even_if_it_does_exist() throws Exception {
        long count = chatRoomRepository.count();
        User user = User.builder()
                .id(2L)
                .email("example2@email.com")
                .password("example2password")
                .build();

        mockMvc.perform(post("/api/posts/1/chat-rooms")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isCreated());

        assertThat(chatRoomRepository.count()).isEqualTo(count);
    }

    @Test
    @DisplayName("채팅방은 존재하지만 active가 false일 때 성공하면 active가 true로 바뀐다.")
    @Transactional
    void should_change_active_value_to_true() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .password("examplepassword")
                .build();

        mockMvc.perform(post("/api/posts/2/chat-rooms")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isCreated());

        ChatRoom chatRoom = chatRoomRepository.findById(2L).get();
        assertThat(chatRoom.getMembers().size()).isEqualTo(2);

        ChatRoomMember member1 = chatRoom.findMemberByUserId(user.getId());
        ChatRoomMember member2 = chatRoom.findOtherMemberByUserId(user.getId());

        assertThat(member1.isActive()).isTrue();
        assertThat(member2.isActive()).isTrue();
    }

    @Test
    @DisplayName("채팅방이 존재하고 상대방 active가 false일 경우 성공해도 상대방의 active가 바뀌지 않는다.")
    @Transactional
    void should_not_change_active_value() throws Exception {
        User user = User.builder()
                .id(3L)
                .email("example@email.com")
                .password("examplepassword")
                .build();

        mockMvc.perform(post("/api/posts/2/chat-rooms")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isCreated());

        ChatRoom chatRoom = chatRoomRepository.findAll(Sort.by(Direction.DESC, "id")).get(0);
        assertThat(chatRoom.getMembers().size()).isEqualTo(2);

        ChatRoomMember member1 = chatRoom.findMemberByUserId(user.getId());
        ChatRoomMember member2 = chatRoom.findOtherMemberByUserId(user.getId());

        assertThat(member1.isActive()).isTrue();
        assertThat(member2.isActive()).isFalse();
    }
}