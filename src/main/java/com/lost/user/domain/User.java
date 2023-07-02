package com.lost.user.domain;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.user.controller.request.UpdateProfileRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_USER_EMAIL", columnNames = {"email"}),
        @UniqueConstraint(name = "UQ_USER_NICKNAME", columnNames = {"nickname"})
}, name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User encryptPassword(PasswordEncoder passwordEncoder) {
        return this.toBuilder()
                .password(passwordEncoder.encode(password))
                .build();
    }

    public User updateProfile(UpdateProfileRequest updateProfileRequest) {
        return this.toBuilder()
                .nickname(updateProfileRequest.getNickname())
                .password(updateProfileRequest.getPassword())
                .build();
    }

    public boolean equalsToPlainPassword(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, password);
    }
}