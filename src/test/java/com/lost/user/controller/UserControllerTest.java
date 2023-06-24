package com.lost.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

@SqlGroup({
        @Sql(value = "/sql/user/insert-user-controller-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-user.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource(value = {"example2,true,사용 가능한 닉네임입니다.", "example,false,이미 사용중인 닉네임입니다."})
    @DisplayName("닉네임 중복 확인")
    void should_return_expected_enable_and_message_when_passed_given_nickname(
            String givenNickname, Boolean expectedEnable, String expectedMessage) throws Exception {
        //expected
        mockMvc.perform(get("/api/users/duplicate/check")
                        .queryParam("nickname", givenNickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enable").value(expectedEnable))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andDo(print());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임 중복 확인")
    void should_return_expected_enable_and_message_when_passed_given_nickname(String nickname) throws Exception {
        //expected
        mockMvc.perform(get("/api/users/duplicate/check")
                        .queryParam("nickname", nickname))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}