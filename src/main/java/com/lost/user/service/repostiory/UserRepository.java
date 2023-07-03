package com.lost.user.service.repostiory;

import com.lost.user.domain.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
