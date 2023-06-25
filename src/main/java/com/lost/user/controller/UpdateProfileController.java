package com.lost.user.controller;

import com.lost.common.domain.exception.ForbiddenException;
import com.lost.security.userdetails.UserPrincipal;
import com.lost.user.controller.request.UpdateProfileRequest;
import com.lost.user.service.UpdateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateProfileController {

    private final UpdateProfileService updateProfileService;

    @PutMapping("/api/users/{userId}/profile")
    public void updateProfile(
            @PathVariable Long userId,
            @RequestBody UpdateProfileRequest updateProfileRequest,
            @AuthenticationPrincipal UserPrincipal user) {
        if (!user.getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
        updateProfileService.update(userId, updateProfileRequest);
    }
}
