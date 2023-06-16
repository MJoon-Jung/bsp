package com.lost.user.infra.entity;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.user.domain.User;
import com.lost.user.domain.UserRole;
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

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_USER_EMAIL", columnNames = {"email"}),
        @UniqueConstraint(name = "UQ_USER_NICKNAME", columnNames = {"nickname"})
}, name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User toModel() {
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static UserJpaEntity from(User user) {
        UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.id = user.getId();
        userJpaEntity.email = user.getEmail();
        userJpaEntity.nickname = user.getNickname();
        userJpaEntity.password = user.getPassword();
        userJpaEntity.role = user.getRole();
        userJpaEntity.setCreatedAt(user.getCreatedAt());
        userJpaEntity.setUpdatedAt(user.getUpdatedAt());
        return userJpaEntity;
    }
}