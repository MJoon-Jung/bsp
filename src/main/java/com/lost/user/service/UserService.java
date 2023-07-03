package com.lost.user.service;

import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.user.controller.response.UserResponse;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Boolean checkDuplicatedNickname(String nickname) {
        Optional<User> maybeUser = userRepository.findByNickname(nickname);
        if (maybeUser.isEmpty()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public UserResponse loadOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER", userId));
        return UserResponse.from(user);
    }
}
