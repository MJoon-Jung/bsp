package com.lost.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserTest {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword + "abcdefghijklmnopqrstuvwxyz1234567890";
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return (rawPassword + "abcdefghijklmnopqrstuvwxyz1234567890").equals(encodedPassword);
        }
    };

    @Test
    @DisplayName("비밀번호 암호화 성공")
    void should_be_success_when_encrypt_password() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .role(UserRole.MEMBER)
                .build();
        //when
        User result = user.encryptPassword(passwordEncoder);
        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(result.getNickname()).isEqualTo("example"),
                () -> assertThat(result.getRole()).isEqualTo(UserRole.MEMBER),
                () -> assertThat(result.getPassword()).isEqualTo(
                        "passwordabcdefghijklmnopqrstuvwxyz1234567890"));
    }

    @DisplayName("암호화된 비밀번호와 일치하는지 확인이 가능")
    @ParameterizedTest
    @CsvSource(value = {"password,true", "password2,false"})
    void should_be_returned_expected_result_when_compare_to_plain_password(String plainPassword, Boolean expected) {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .role(UserRole.MEMBER)
                .build();
        User hashPasswordUser = user.encryptPassword(passwordEncoder);
        //when
        boolean result = hashPasswordUser.equalsToPlainPassword(plainPassword, passwordEncoder);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("개인정보 수정 성공")
    void should_be_update_success_when_updateProfile() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("password")
                .role(UserRole.MEMBER)
                .build();
        //when
        User result = user.updateProfile("example2", "password2");
        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEmail()).isEqualTo("example@email.com"),
                () -> assertThat(result.getNickname()).isEqualTo("example2"),
                () -> assertThat(result.getRole()).isEqualTo(UserRole.MEMBER),
                () -> assertThat(result.getPassword()).isEqualTo("password2"));
    }
}