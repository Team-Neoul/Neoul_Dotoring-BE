package com.theZ.dotoring.app.notification.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.theZ.dotoring.app.notification.model.Notification;
import com.theZ.dotoring.app.notification.model.QNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationQDSLRepository {

    private final EntityManager em;

    @Override
    public List<Notification> getNotificationByFilter(String title, String goal, Boolean isClose) {

        // JPA 쿼리 생성
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 조회용 Q 객체 생성
        QNotification notification = QNotification.notification;

        // 동적 쿼리
        return queryFactory
                .selectFrom(notification)
                .where(
                        containsTitle(title),
                        containsGoals(goal),
                        isEqualToIsClose(isClose)
                )
                .orderBy(
                        notification.isClose.desc(),
                        notification.curParticipation.desc(),
                        notification.createdAt.desc()
                )
                .fetch();
    }

    private BooleanExpression containsTitle(String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }
        return QNotification.notification.title.contains(title);
    }

    private BooleanExpression containsGoals(String goal) {
        if (goal == null || goal.isEmpty()) {
            return null;
        }

        return QNotification.notification.notificationGoals.any().eq(goal);
    }


    private BooleanExpression isEqualToIsClose(Boolean isClose) {
        if (isClose == null) {
            return null;
        }
        return QNotification.notification.isClose.eq(isClose);
    }

}
