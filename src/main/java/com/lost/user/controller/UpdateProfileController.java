package com.lost.user.controller;

import com.lost.user.controller.request.UpdateProfileRequest;
import com.lost.user.service.UpdateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateProfileController {

    private final UpdateProfileService updateProfileService;

    @PutMapping("/api/user/{userId}/profile")
    public void updateProfile(@PathVariable Long userId, UpdateProfileRequest updateProfileRequest) {
        updateProfileService.update(userId, updateProfileRequest);
    }
}
