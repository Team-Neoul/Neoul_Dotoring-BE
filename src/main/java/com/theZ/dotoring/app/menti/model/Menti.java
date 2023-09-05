package com.theZ.dotoring.app.menti.model;


import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.common.CommonEntity;
import com.theZ.dotoring.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menti extends CommonEntity {

    @Id
    private Long mentiId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentoId")
    private MemberAccount memberAccount;

    @Column(unique = true)
    @Size(min = 3, max = 8)
    private String nickname;

    @Size(min = 10, max = 100)
    private String introduction;

    private String school;

    private Long grade;

    @Size(min = 10, max = 300)
    private String preferredMentoring;

    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "menti")
    private List<DesiredField> desiredFields = new ArrayList<>();

    @OneToMany(mappedBy = "menti")
    private List<MemberMajor> memberMajors = new ArrayList<>();

    private Long viewCount;

    @Builder
    public Menti(String nickname, String introduction, String school, Long grade) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.school = school;
        this.grade = grade;
        this.status = Status.WAIT;
        this.viewCount = 0L;
    }

    public static Menti createMenti(String nickname, String introduction, String school, Long grade, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Menti menti = Menti.builder()
                .nickname(nickname)
                .introduction(introduction)
                .school(school)
                .grade(grade)
                .build();
        menti.mappingMemberAccount(memberAccount);
        menti.mappingProfile(profile);
        menti.addDesiredFields(desiredFields);
        menti.addMemberMajors(memberMajors);
        return menti;
    }

    public void updateViewCount(){
        this.viewCount ++;
    }

    public void mappingProfile(Profile profile){
        this.profile = profile;
    }

    private void mappingMemberAccount(MemberAccount memberAccount){
        this.memberAccount = memberAccount;
    }

    // todo 멘티 학년 재 설정 요청보내기! - 매학기 시작할 때마다

    private void addDesiredFields(List<DesiredField> desiredFields){
        if(desiredFields.isEmpty()){
            throw new IllegalArgumentException("희망 멘토링 분야가 1개 이상 있어야합니다.");
        }
        for(DesiredField desiredField : desiredFields){
            desiredField.mappingMenti(this);
            this.desiredFields.add(desiredField);
        }
    }

    private void addMemberMajors(List<MemberMajor> memberMajors){
        if(memberMajors.isEmpty()){
            throw new IllegalArgumentException("1개 이상 학과를 가지고 있어야합니다.");
        }
        for(MemberMajor memberMajor : memberMajors){
            memberMajor.mappingMenti(this);
            this.memberMajors.add(memberMajor);
        }
    }

    public void updateDesiredField(List<DesiredField> desiredFields) {
        if(desiredFields.isEmpty()){
            throw new IllegalArgumentException("희망 멘토링 분야가 1개 이상 있어야합니다.");
        }
        this.desiredFields.clear();
        for(DesiredField desiredField : desiredFields){
            desiredField.mappingMenti(this);
            this.desiredFields.add(desiredField);
        }
    }

    public void updatePreferredMentoring(String preferredMentoring) {
        this.preferredMentoring = preferredMentoring;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void approveStatus() {
        this.status = Status.ACTIVE;
    }
}
