package com.lost.user.service.repostiory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import com.lost.user.infra.repository.UserJpaRepository;
import com.lost.user.service.repostiory.UserRepositoryTest.TestConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
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

        userRepository.save(user);
        //when
        Optional<User> maybeUser = userRepository.findByEmail("example@email.com");
        //then
        assertThat(maybeUser).isPresent();
        User findUser = maybeUser.get();
        assertAll(
                () -> assertThat(findUser.getId()).isNotNull(),
                () -> assertThat(findUser.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(findUser.getNickname()).isEqualTo("example"),
                () -> assertThat(findUser.getPassword()).isEqualTo("password")
        );
    }

    @Test
    @DisplayName("닉네임으로 유저 찾기 성공")
    void should_return_user_when_findByNickname() {
        //given
        User user = User.builder()
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .role(UserRole.MEMBER)
                .build();

        userRepository.save(user);
        //when
        Optional<User> maybeUser = userRepository.findByNickname("example");
        //then
        assertThat(maybeUser).isPresent();
        User findUser = maybeUser.get();
        assertAll(
                () -> assertThat(findUser.getId()).isNotNull(),
                () -> assertThat(findUser.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(findUser.getNickname()).isEqualTo("example"),
                () -> assertThat(findUser.getPassword()).isEqualTo("password")
        );
    }

    @TestConfiguration
    public static class TestConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }
}