package com.theZ.dotoring.app.notification.repository;

import com.theZ.dotoring.app.notification.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class NotificationRepositoryImplTest {

    @Autowired
    private NotificationRepositoryImpl notificationRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void test_GetNotificationByFilter() {
        // 테스트 데이터 삽입
        Notification notification1 = Notification.builder()
                .title("제목1")
                .notificationGoals(List.of("goal1", "goal2"))
                .maxRecruitment(10)
                .build();

        Notification notification2 = Notification.builder()
                .title("제목2")
                .notificationGoals(List.of("goal2", "goal3"))
                .maxRecruitment(10)
                .build();

        Notification notification3 = Notification.builder()
                .title("제목3")
                .notificationGoals(List.of("goal3", "goal4"))
                .maxRecruitment(10)
                .build();

        Notification notification4 = Notification.builder()
                .title("제목2")
                .notificationGoals(List.of("goal5", "goal6"))
                .maxRecruitment(10)
                .build();

        notification1.getParticipations().add("이승건1");
        notification1.getParticipations().add("이승건2");


        entityManager.persist(notification1);
        entityManager.persist(notification2);
        entityManager.persist(notification3);
        entityManager.persist(notification4);
        entityManager.flush();

        // 테스트 수행
        String title = "제목";
        String goal = "goal2";
        boolean isClose = false;

        List<Notification> result = notificationRepository.getNotificationByFilter(title, goal, null);

        // eye
        for (Notification notification : result) {
            System.out.println(notification.getTitle());
            System.out.println(notification.getParticipations().size());
        }

        // 검증
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("제목1");
        assertThat(result.get(0).getNotificationGoals()).containsExactlyInAnyOrder("goal1", "goal2");
        assertThat(result.get(0).isClose()).isFalse();


    }
}
