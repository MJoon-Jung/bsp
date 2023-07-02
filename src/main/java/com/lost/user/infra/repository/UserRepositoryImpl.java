package com.lost.user.infra.repository;

import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(User::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(User.from(user)).toModel();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(User::toModel);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return userJpaRepository.findByNickname(nickname).map(User::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
