package com.lost.user.domain;

import com.lost.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class User {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String password;
    private final UserRole role;
    private final List<Post> posts;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public User(Long id, String email, String nickname, String password, UserRole role, List<Post> posts,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.posts = posts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User hashPassword(PasswordEncoder passwordEncoder) {
        return this.toBuilder()
                .password(passwordEncoder.encode(password))
                .build();
    }

    public User updateProfile(String nickname, String password) {
        return this.toBuilder()
                .nickname(nickname)
                .password(password)
                .build();
    }

    public boolean equalsToPlainPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
