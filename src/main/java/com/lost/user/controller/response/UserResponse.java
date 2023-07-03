package com.lost.user.controller.response;

import com.lost.post.controller.response.PostResponse;
import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final UserRole role;
    private final List<PostResponse> posts;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    @QueryProjection
    public UserResponse(Long id, String email, String nickname, UserRole role, List<PostResponse> posts,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.posts = posts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .posts(user.getPosts().stream().map(PostResponse::from).toList())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
