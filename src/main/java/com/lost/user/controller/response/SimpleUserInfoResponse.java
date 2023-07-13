package com.lost.user.controller.response;

import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleUserInfoResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public SimpleUserInfoResponse(Long id, String email, String nickname, UserRole role,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SimpleUserInfoResponse from(User user) {
        return SimpleUserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
