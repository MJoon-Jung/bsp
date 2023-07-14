package com.lost.notifiaction.controller;

import com.lost.notifiaction.infra.repository.NotificationRepository;
import com.lost.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @PatchMapping("/api/notifications/read")
    public void read(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        notificationRepository.read(userPrincipal.getUserId());
    }
}
