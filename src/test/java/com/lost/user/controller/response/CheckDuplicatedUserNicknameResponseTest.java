package com.lost.user.controller.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CheckDuplicatedUserNicknameResponseTest {

    @DisplayName("생성")
    @ParameterizedTest
    @CsvSource(value = {"true,사용 가능한 닉네임입니다.", "false,이미 사용중인 닉네임입니다."})
    void should_be_equal_to_expected_message_when_passed_enable_parameter(Boolean enable, String message) {
        //given
        CheckDuplicatedUserNicknameResponse response = CheckDuplicatedUserNicknameResponse.builder()
                .enable(enable)
                .build();
        //when
        //then
        assertThat(response.getEnable()).isEqualTo(enable);
        assertThat(response.getMessage()).isEqualTo(message);
    }
}