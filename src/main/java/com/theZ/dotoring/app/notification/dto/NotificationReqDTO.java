package com.theZ.dotoring.app.notification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NotificationReqDTO {

    private String title;

    private List<String> goals;

    private String introduction;

    private int maxRecruitment;

}
