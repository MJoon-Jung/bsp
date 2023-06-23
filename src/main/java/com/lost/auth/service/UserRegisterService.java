package com.lost.auth.service;

import com.lost.auth.controller.request.SignUpRequest;
import com.lost.common.domain.exception.UserAlreadyExistsException;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest signUpRequest) {
        Optional<User> maybeUser = userRepository.findByEmail(signUpRequest.getEmail());
        if (maybeUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = User.from(signUpRequest);
        user = user.encryptPassword(passwordEncoder);
        userRepository.save(user);
    }
}