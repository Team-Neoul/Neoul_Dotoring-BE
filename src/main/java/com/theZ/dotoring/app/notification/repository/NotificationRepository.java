package com.theZ.dotoring.app.notification.repository;

import com.theZ.dotoring.app.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
