package com.lost.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.auth.controller.request.SignUpRequest;
import com.lost.user.domain.User;
import com.lost.user.fake.FakeUserRepository;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserSignUpServiceTest {

    private UserSignUpService userSignUpService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword + "abcdefghijklmnopqrstuvwxyz1234567890";
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return (rawPassword + "abcdefghijklmnopqrstuvwxyz1234567890").equals(encodedPassword);
            }
        };

        userRepository = new FakeUserRepository();
        userSignUpService = new UserSignUpService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 성공")
    void should_be_sign_up_success_when_passed_correct_request() {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build();

        //when
        userSignUpService.signUp(signUpRequest);
        //then
        Optional<User> maybeUser = userRepository.findByEmail("example@email.com");
        assertThat(maybeUser.isPresent()).isTrue();

        User user = maybeUser.get();
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(signUpRequest.getEmail()),
                () -> assertThat(user.getNickname()).isEqualTo(signUpRequest.getNickname()),
                () -> assertThat(user.equalsToPlainPassword("password", passwordEncoder)).isTrue()
        );
    }
}