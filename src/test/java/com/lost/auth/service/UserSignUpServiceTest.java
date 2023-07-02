package com.lost.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.fake.TestContainer;
import com.lost.user.controller.request.SignUpRequest;
import com.lost.user.service.UserSignUpService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserSignUpServiceTest {

    private UserSignUpService userSignUpService;
    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        userSignUpService = new UserSignUpService(testContainer.userRepository, testContainer.passwordEncoder);
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
        Optional<User> maybeUser = testContainer.userRepository.findByEmail("example@email.com");
        assertThat(maybeUser.isPresent()).isTrue();

        User user = maybeUser.get();
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(signUpRequest.getEmail()),
                () -> assertThat(user.getNickname()).isEqualTo(signUpRequest.getNickname()),
                () -> assertThat(user.equalsToPlainPassword("password", testContainer.passwordEncoder)).isTrue()
        );
    }
}