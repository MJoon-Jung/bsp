package com.lost.fake;

import com.lost.image.service.repository.ImagePostRepository;
import com.lost.post.service.repository.PostRepository;
import com.lost.user.service.repostiory.UserRepository;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestContainer {

    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final ImagePostRepository imagePostRepository;
    public final PasswordEncoder passwordEncoder;

    @Builder
    public TestContainer() {
        userRepository = new FakeUserRepository();
        imagePostRepository = new FakeImagePostRepository();
        postRepository = FakePostRepository.builder()
                .imagePostRepository(imagePostRepository)
                .userRepository(userRepository)
                .build();
        passwordEncoder = new FakePasswordEncoder();
    }
}
