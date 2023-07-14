package com.lost.notifiaction.domain;

import com.lost.common.infra.entity.BaseTimeJpaEntity;
import com.lost.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User targetUser;
    @Column(name = "READ_STATUS", columnDefinition = "boolean")
    @ColumnDefault("false")
    private Boolean read;

    @Builder
    public Notification(NotificationType type, User targetUser, Boolean read) {
        this.type = type;
        this.targetUser = targetUser;
        this.read = read != null && read;
    }
}
