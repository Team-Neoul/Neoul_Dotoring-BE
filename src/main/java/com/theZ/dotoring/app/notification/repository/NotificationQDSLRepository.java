package com.theZ.dotoring.app.notification.repository;

import com.theZ.dotoring.app.notification.model.Notification;

import java.util.List;

public interface NotificationQDSLRepository {

    List<Notification> getNotificationByFilter(String title, String goal, Boolean isClose);

}
