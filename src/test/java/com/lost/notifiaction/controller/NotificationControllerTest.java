package com.lost.notifiaction.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lost.notifiaction.domain.Notification;
import com.lost.notifiaction.infra.repository.NotificationRepository;
import com.lost.security.userdetails.UserPrincipal;
import com.lost.user.domain.User;
import com.lost.user.service.repostiory.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SqlGroup({
        @Sql(value = "/sql/insert-chat-controller-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@Transactional
@SpringBootTest
class NotificationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("읽지 않은 알림들 전부 읽음 처리 성공")
    void should_read_value_true_when_read_notification() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .nickname("example")
                .password("examplepassword")
                .build();
        user = userRepository.save(user);

        Notification notification = Notification.builder()
                .targetUser(user)
                .message("message")
                .read(true)
                .build();
        Notification notification2 = Notification.builder()
                .targetUser(user)
                .message("message")
                .build();
        Notification notification3 = Notification.builder()
                .targetUser(user)
                .message("message")
                .build();
        notificationRepository.saveAll(List.of(notification, notification2, notification3));

        //when
        //then
        mockMvc.perform(patch("/api/notifications/read")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isOk());

        List<Notification> notifications = notificationRepository.findByTargetUserIdAndRead(user.getId(), false);
        assertThat(notifications.size()).isEqualTo(0);
    }
}