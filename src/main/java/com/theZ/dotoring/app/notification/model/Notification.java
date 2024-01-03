package com.theZ.dotoring.app.notification.model;

import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.notification.dto.NotificationReqDTO;
import com.theZ.dotoring.common.CommonEntity;
import com.theZ.dotoring.common.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Notification extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String introduction;

    // Converter 사용시 Querydsl contain 사용 불가
    //@Convert(converter = StringListConverter.class)
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "NOTIFICATION_GOAL")
    private List<String> notificationGoals;

    // 최대 모집 인원
    private Integer maxRecruitment;

    // 현재 모집 인원
    @Builder.Default
    private int curRecruitment = 0;

    // 현재 참여 인원
    @Builder.Default
    private int curParticipation = 0;

    @Builder.Default
    private boolean isClose = false;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> participations = new ArrayList<>();


    public static Notification of(NotificationReqDTO notificationReqDTO, String memberNickname){
        return Notification.builder()
                .title(notificationReqDTO.getTitle())
                .author(memberNickname)
                .notificationGoals(notificationReqDTO.getGoals())
                .maxRecruitment(notificationReqDTO.getMaxRecruitment())
                .build();
    }

    public void updateNotification(NotificationReqDTO notificationUpdateReqDTO){
        if (!notificationUpdateReqDTO.getTitle().isEmpty()){
            this.title = notificationUpdateReqDTO.getTitle();
        }
        if (!notificationUpdateReqDTO.getGoals().isEmpty()){
            this.notificationGoals = notificationUpdateReqDTO.getGoals();
        }
        if (!notificationUpdateReqDTO.getIntroduction().isEmpty()){
            this.introduction = notificationUpdateReqDTO.getIntroduction();
        }
        if (notificationUpdateReqDTO.getMaxRecruitment() != 0){
            this.maxRecruitment = notificationUpdateReqDTO.getMaxRecruitment();
        }
    }

    public void updateStatusToIsClose(){
        this.isClose = true;
    }
}
