package com.lost.notifiaction.infra.repository;

import com.lost.notifiaction.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByTargetUserIdAndRead(Long targetUserId, Boolean read);

    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.targetUser.id=:userId and n.read = false")
    void read(@Param("userId") Long userId);
}
