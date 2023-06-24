package com.lost.user.service;

import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
}
