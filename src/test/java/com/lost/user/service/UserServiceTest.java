package com.lost.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.lost.fake.FakeUserRepository;
import com.lost.user.service.repostiory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("닉네임이 중복되지 않으면 true 를 반환한다.")
    void should_return_true_when_nickname_is_unique() {
        //given
        //when
        Boolean result = userService.checkDuplicatedNickname("example");
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임이 중복되면 false 를 반환한다.")
    void should_return_true_when_nickname_is_not_unique() {
        //given
        userRepository.save(User.builder()
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .build());
        //when
        Boolean result = userService.checkDuplicatedNickname("example");
        //then
        assertThat(result).isFalse();
    }
}