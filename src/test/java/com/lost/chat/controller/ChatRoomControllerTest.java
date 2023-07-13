package com.lost.chat.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lost.security.userdetails.UserPrincipal;
import com.lost.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SqlGroup({
        @Sql(value = "/sql/insert-chat-repository-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
class ChatRoomControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("채팅방 불러오기 성공")
    void should_return_user_chat_rooms() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .password("examplepassword")
                .build();

        mockMvc.perform(get("/api/chat-rooms")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].member.id", not(user.getId())));
    }

    @Test
    @DisplayName("채팅을 불러올 때는 updatedAt을 기점으로 가져온다.")
    void should_return_chats_after_UpdatedAt_when_fetching_chats() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .password("examplepassword")
                .build();

        mockMvc.perform(get("/api/chat-rooms/2/chats")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(6)));
    }

    @Test
    @DisplayName("채팅을 불러올 때는 updatedAt을 기점으로 가져온다.")
    void should_return_chats_after_activateUpdatedAt_when_fetching_chats2() throws Exception {
        User user = User.builder()
                .id(2L)
                .email("example@email.com")
                .password("examplepassword")
                .build();

        mockMvc.perform(get("/api/chat-rooms/2/chats")
                        .contentType("application/json")
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }
}