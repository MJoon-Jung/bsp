package com.lost.user.controller.response;

import com.lost.notifiaction.controller.response.NotificationResponse;
import com.lost.notifiaction.domain.Notification;
import com.lost.post.controller.response.PostResponse;
import com.lost.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyInfoResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final List<PostResponse> writePosts;
    private final List<PostResponse> findPosts;
    private final List<NotificationResponse> notifications;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public MyInfoResponse(Long id, String email, String nickname, List<PostResponse> writePosts,
            List<PostResponse> findPosts,
            List<NotificationResponse> notifications, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.writePosts = writePosts;
        this.findPosts = findPosts;
        this.notifications = notifications;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MyInfoResponse from(User user, List<Notification> notifications) {
        return MyInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .writePosts(user.getWritePosts().stream().map(PostResponse::from).toList())
                .findPosts(user.getFindPosts().stream().map(PostResponse::from).toList())
                .notifications(notifications.stream().map(NotificationResponse::from).toList())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
