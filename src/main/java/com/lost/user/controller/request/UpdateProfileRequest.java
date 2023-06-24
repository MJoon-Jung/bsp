package com.lost.user.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateProfileRequest {

    private final String nickname;
    private final String password;

    @Builder
    public UpdateProfileRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
