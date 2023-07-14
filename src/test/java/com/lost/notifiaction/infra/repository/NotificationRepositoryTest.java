package com.lost.notifiaction.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lost.notifiaction.domain.Notification;
import com.lost.notifiaction.domain.NotificationType;
import com.lost.user.domain.User;
import com.lost.user.infra.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
class NotificationRepositoryTest {

    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @DisplayName("읽지 않은 알림만 불러오기 성공")
    void should_return_not_read_notification_when_fetching_notification_by_userId_and_read() {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("examplepassword")
                .build();
        user = userRepository.save(user);

        Notification notification = Notification.builder()
                .type(NotificationType.CHAT)
                .message("message")
                .targetUser(user)
                .build();
        Notification notification2 = Notification.builder()
                .type(NotificationType.CHAT)
                .message("message2")
                .targetUser(user)
                .build();
        Notification notification3 = Notification.builder()
                .type(NotificationType.CHAT)
                .message("message3")
                .targetUser(user)
                .read(true)
                .build();
        notificationRepository.saveAll(List.of(notification, notification2, notification3));
        //when
        notificationRepository.findAll();
        List<Notification> findNotification = notificationRepository.findByTargetUserIdAndRead(user.getId(), false);
        //then
        assertThat(findNotification.size()).isEqualTo(2);
    }
}