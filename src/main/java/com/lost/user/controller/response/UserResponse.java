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
    private final List<PostResponse> writePosts;
    private final List<PostResponse> findPosts;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    @QueryProjection
    public UserResponse(Long id, String email, String nickname, UserRole role, List<PostResponse> writePosts,
            List<PostResponse> findPosts, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.writePosts = writePosts;
        this.findPosts = findPosts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .writePosts(user.getWritePosts().stream().map(PostResponse::from).toList())
                .findPosts(user.getFindPosts().stream().map(PostResponse::from).toList())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
