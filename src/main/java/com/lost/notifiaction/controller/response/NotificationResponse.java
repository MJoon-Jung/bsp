package com.lost.notifiaction.controller.response;

import com.lost.notifiaction.domain.Notification;
import com.lost.notifiaction.domain.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationResponse {

    private final Long id;
    private final NotificationType type;
    private final String message;

    @Builder
    public NotificationResponse(Long id, NotificationType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .message(notification.getMessage())
                .build();
    }
}
