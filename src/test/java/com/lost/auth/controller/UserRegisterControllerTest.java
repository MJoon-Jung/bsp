package com.lost.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lost.user.controller.request.SignUpRequest;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

@SqlGroup({
        @Sql(value = "/sql/user/delete-user.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@AutoConfigureMockMvc
@SpringBootTest
class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void request_signup_user_returned_user() throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        String json = objectMapper.writeValueAsString(signUpRequest);
        //when
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
        //then
        Optional<User> maybeUser = userRepository.findByEmail("example@email.com");
        assertThat(maybeUser.isPresent()).isTrue();

        User user = maybeUser.get();
        assertAll(
                () -> assertThat(user.getId()).isNotNull(),
                () -> assertThat(user.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(user.equalsToPlainPassword("password", passwordEncoder)).isTrue(),
                () -> assertThat(user.getNickname()).isEqualTo("example"));
    }

    @Test
    @DisplayName("회원가입 - 이메일 중복")
    void test_fail_when_duplicated_email() throws Exception {
        //given
        userRepository.save(User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example2")
                .password("password")
                .build());

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();
        String json = objectMapper.writeValueAsString(signUpRequest);
        //when
        //then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("이미 존재하는 회원입니다."))
                .andDo(print());
    }
    //Todo 닉네임 중복 실패 구현

    @DisplayName("회원가입 실패 - 이메일 검증")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "emsamdsfadsdasfas", "asdf.com"})
    void a(String email) throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email(email)
                .nickname("example")
                .password("password")
                .build();
        String json = objectMapper.writeValueAsString(signUpRequest);
        //when
        //then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").isNotEmpty())
                .andDo(print());
    }

    @DisplayName("회원가입 실패 - 닉네임 검증")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void b(String nickname) throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("example@email.com")
                .nickname(nickname)
                .password("password")
                .build();
        String json = objectMapper.writeValueAsString(signUpRequest);
        //when
        //then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.nickname").isNotEmpty())
                .andDo(print());
    }

    @DisplayName("회원가입 실패 - 패스워드 검증")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ",})
    void c(String password) throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("example@email.com")
                .nickname("example")
                .password(password)
                .build();
        String json = objectMapper.writeValueAsString(signUpRequest);
        //when
        //then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.password").isNotEmpty())
                .andDo(print());
    }
}