package com.lost.user.service.repostiory;

import com.lost.user.domain.User;
import com.lost.user.infra.entity.UserJpaEntity;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByNickname(String nickname);

    UserJpaEntity save(UserJpaEntity userJpaEntity);
}
