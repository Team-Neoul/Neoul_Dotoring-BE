package com.theZ.dotoring.app.mento.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindMyMentoRespDTO {

    private Long mentoId;
    private String profileImage;
    private String nickname;
    private List<String> fields;
    private List<String> majors;
    private List<String> tags;
    private Long grade;
    private String mentoringSystem;

    @Builder
    public FindMyMentoRespDTO(Long mentoId, String profileImage, String nickname, List<String> fields, List<String> majors, List<String> tags, Long grade, String mentoringSystem) {
        this.mentoId = mentoId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.fields = fields;
        this.majors = majors;
        this.tags = tags;
        this.grade = grade;
        this.mentoringSystem = mentoringSystem;
    }
}
