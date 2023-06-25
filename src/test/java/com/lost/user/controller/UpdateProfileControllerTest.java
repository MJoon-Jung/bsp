package com.lost.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lost.security.userdetails.UserPrincipal;
import com.lost.user.controller.request.UpdateProfileRequest;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SqlGroup({
        @Sql(value = "/sql/user/insert-user-controller-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-user.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
class UpdateProfileControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @DisplayName("유저 프로필 수정 성공")
    void should_be_success_when_user_update_profile() throws Exception {
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .nickname("example2")
                .password("examplepassword2")
                .build();
        String json = objectMapper.writeValueAsString(updateProfileRequest);
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .password("examplepassword")
                .build();
        //expected
        mockMvc.perform(put("/api/users/{userId}/profile", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .with(user(new UserPrincipal(user))))
                .andExpect(status().isOk())
                .andDo(print());

        User findUser = userRepository.findById(1L).get();
        assertAll(
                () -> assertThat(findUser.getNickname()).isEqualTo("example2"),
                () -> assertThat(findUser.equalsToPlainPassword("examplepassword2", passwordEncoder)).isTrue()
        );
    }

    @Test
    @DisplayName("로그인하지 않은 유저가 프로필 수정하면 실패")
    void should_fail_when_user_is_not_authenticated() throws Exception {
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .nickname("example2")
                .password("examplepassword2")
                .build();
        String json = objectMapper.writeValueAsString(updateProfileRequest);
        //expected
        mockMvc.perform(put("/api/users/{userId}/profile", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("다른 유저가 프로필 수정을 시도하면 실패")
    void should_fail_when_another_user_tries_to_update_profile() throws Exception {
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .nickname("example2")
                .password("examplepassword2")
                .build();
        String json = objectMapper.writeValueAsString(updateProfileRequest);

        User user = User.builder()
                .id(2L)
                .email("other@email.com")
                .password("otherpassword")
                .build();

        //expected
        mockMvc.perform(put("/api/users/{userId}/profile", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .with(user(new UserPrincipal(user))))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}