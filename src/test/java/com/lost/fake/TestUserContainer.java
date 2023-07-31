package com.lost.fake;

import com.lost.user.service.repostiory.UserRepository;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestUserContainer {

    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    @Builder
    public TestUserContainer() {
        userRepository = new FakeUserRepository();
        passwordEncoder = new FakePasswordEncoder();
    }
}
