package com.theZ.dotoring.app.mento.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindMentoByIdRespDTO {

    private Long mentoId;
    private String profileImage;
    private String nickname;
    private String mentoringSystem;
    private List<String> fields;
    private List<String> majors;
    private String introduction;

    @Builder
    public FindMentoByIdRespDTO(Long mentoId, String profileImage, String nickname, String mentoringSystem, List<String> fields, List<String> majors, String introduction) {
        this.mentoId = mentoId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.mentoringSystem = mentoringSystem;
        this.fields = fields;
        this.majors = majors;
        this.introduction = introduction;
    }
}
