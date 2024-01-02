package com.theZ.dotoring.app.notification.dto;

import com.theZ.dotoring.app.notification.model.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class NotificationResDTO {

    private List<NotificationDTO> notificationDTOS;

    public NotificationResDTO(List<Notification> notifications){
        this.notificationDTOS = notifications.stream().map(NotificationDTO::new).collect(Collectors.toList());
    }

    public static class NotificationDTO {

        private Long id;

        private String notificationName;

        private String writer;

        private LocalDateTime createAt;

        private int applicantsNum;

        private boolean isClose;

        public NotificationDTO(Notification notification){
            this.id = notification.getId();
            this.notificationName = notification.getTitle();
            this.writer = notification.getAuthor();
            this.createAt = notification.getCreatedAt();
            this.applicantsNum = notification.getCurRecruitment();
            this.isClose = notification.isClose();
        }
    }
}
