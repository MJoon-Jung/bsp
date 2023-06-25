package com.lost.user.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateProfileRequest {

    private String nickname;
    private String password;

    @Builder
    public UpdateProfileRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
