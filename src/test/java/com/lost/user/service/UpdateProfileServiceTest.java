package com.lost.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.user.controller.request.UpdateProfileRequest;
import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import com.lost.user.fake.FakeUserRepository;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class UpdateProfileServiceTest {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UpdateProfileService updateProfileService;

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
        updateProfileService = new UpdateProfileService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("유저 프로필 수정 성공")
    void should_be_profile_updated_when_user_update_profile() {
        //given
        User user = userRepository.save(User.builder()
                .email("example@email.com")
                .nickname("example")
                .password("examplepassword")
                .role(UserRole.MEMBER)
                .build());

        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .nickname("example2")
                .password("examplepassword2")
                .build();
        //when
        updateProfileService.update(user.getId(), updateProfileRequest);
        //then
        Optional<User> maybeUser = userRepository.findById(user.getId());
        assertThat(maybeUser.isPresent()).isTrue();

        User findUser = maybeUser.get();
        assertAll(
                () -> assertThat(findUser.getId()).isEqualTo(user.getId()),
                () -> assertThat(findUser.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(findUser.equalsToPlainPassword("examplepassword2", passwordEncoder)).isTrue(),
                () -> assertThat(findUser.getNickname()).isEqualTo("example2")
        );
    }
}