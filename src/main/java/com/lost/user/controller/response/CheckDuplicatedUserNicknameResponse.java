package com.lost.user.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckDuplicatedUserNicknameResponse {

    private static final String NOT_DUPLICATE_NICKNAME = "사용 가능한 닉네임입니다.";
    private static final String DUPLICATE_NICKNAME = "이미 사용중인 닉네임입니다.";
    private final Boolean enable;
    private final String message;

    @Builder
    public CheckDuplicatedUserNicknameResponse(Boolean enable) {
        this.enable = enable;
        this.message = enable ? NOT_DUPLICATE_NICKNAME : DUPLICATE_NICKNAME;
    }

    public static CheckDuplicatedUserNicknameResponse from(Boolean enable) {
        return CheckDuplicatedUserNicknameResponse.builder()
                .enable(enable)
                .build();
    }
}
