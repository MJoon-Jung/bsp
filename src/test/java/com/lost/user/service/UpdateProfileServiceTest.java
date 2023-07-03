package com.lost.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.fake.TestContainer;
import com.lost.user.controller.request.UpdateProfileRequest;
import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateProfileServiceTest {

    private UpdateProfileService updateProfileService;
    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        updateProfileService = new UpdateProfileService(testContainer.userRepository, testContainer.passwordEncoder);
    }

    @Test
    @DisplayName("유저 프로필 수정 성공")
    void should_be_profile_updated_when_user_update_profile() {
        //given
        User user = testContainer.userRepository.save(User.builder()
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
        Optional<User> maybeUser = testContainer.userRepository.findById(user.getId());
        assertThat(maybeUser.isPresent()).isTrue();

        User findUser = maybeUser.get();
        assertAll(
                () -> assertThat(findUser.getId()).isEqualTo(user.getId()),
                () -> assertThat(findUser.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(
                        findUser.equalsToPlainPassword("examplepassword2", testContainer.passwordEncoder)).isTrue(),
                () -> assertThat(findUser.getNickname()).isEqualTo("example2")
        );
    }
}