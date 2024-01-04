package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindMyMentiRespDTO {

    private Long mentiId;
    private String profileImage;
    private String nickname;
    private List<String> fields;
    private List<String> majors;
    private List<String> tags;
    private Long grade;
    private String preferredMentoring;
    private String school;

    @Builder
    public FindMyMentiRespDTO(Long mentiId, String profileImage, String nickname, List<String> fields, List<String> majors, List<String> tags, Long grade, String preferredMentoring, String school) {
        this.mentiId = mentiId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.fields = fields;
        this.majors = majors;
        this.tags = tags;
        this.grade = grade;
        this.preferredMentoring = preferredMentoring;
        this.school = school;
    }
}
