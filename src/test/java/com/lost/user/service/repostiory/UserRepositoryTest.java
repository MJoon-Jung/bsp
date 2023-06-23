package com.lost.user.service.repostiory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import com.lost.user.infra.entity.UserJpaEntity;
import com.lost.user.infra.repository.UserJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {


    @Autowired
    private UserJpaRepository userRepository;

    @Test
    @DisplayName("이메일로 유저 찾기 성공")
    void should_return_user_when_findByEmail() {
        //given
        User user = User.builder()
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .role(UserRole.MEMBER)
                .build();

        userRepository.save(UserJpaEntity.from(user));
        //when
        Optional<UserJpaEntity> maybeUser = userRepository.findByEmail("example@email.com");
        //then
        assertThat(maybeUser).isPresent();
        UserJpaEntity findUser = maybeUser.get();
        assertAll(
                () -> assertThat(findUser.getId()).isNotNull(),
                () -> assertThat(findUser.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(findUser.getNickname()).isEqualTo("example"),
                () -> assertThat(findUser.getPassword()).isEqualTo("password")
        );
    }
}