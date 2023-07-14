package com.lost.notifiaction.infra.repository;

import com.lost.notifiaction.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByTargetUserIdAndRead(Long targetUserId, Boolean read);
}
