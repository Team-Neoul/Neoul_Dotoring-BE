package com.theZ.dotoring.app.notification.dto;

import com.theZ.dotoring.app.notification.model.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NotificationUpdateResDTO {

    private String title;

    private String author;

    private String authorMajor;

    private List<String> goals;

    private String introduction;

    private int maxRecruitment;

    private int curRecruitment;

    private int curParticipation;


    public static NotificationUpdateResDTO of(Notification notification, String memberName, String memberMajor){
        return NotificationUpdateResDTO.builder()
                .title(notification.getTitle())
                .author(memberName)
                .authorMajor(memberMajor)
                .goals(notification.getNotificationGoals())
                .introduction(notification.getIntroduction())
                .maxRecruitment(notification.getMaxRecruitment())
                .curRecruitment(notification.getCurRecruitment())
                .curParticipation(notification.getCurParticipation())
                .build();
    }
}
