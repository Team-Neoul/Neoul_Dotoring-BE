package com.theZ.dotoring.app.notification.dto;

import com.theZ.dotoring.app.notification.model.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationStatusDTO {

    public boolean isClose;

    public static NotificationStatusDTO from(Notification notification){
        return NotificationStatusDTO.builder()
                .isClose(notification.isClose())
                .build();
    }

}
