package com.lost.user.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.user.controller.request.UpdateProfileRequest;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void update(Long userId, UpdateProfileRequest updateProfileRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER", userId));

        user = user.updateProfile(updateProfileRequest);
        user = user.encryptPassword(passwordEncoder);
        userRepository.save(user);
    }
}
