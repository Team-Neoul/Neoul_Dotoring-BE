package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllMentiRespDTO {

    private Long id;
    private String profileImage;
    private String nickname;
    private String preferredMentoringSystem;
    private List<String> fields;
    private List<String> majors;
    private String introduction;

    @Builder
    public FindAllMentiRespDTO(Long id, String profileImage, String nickname, String preferredMentoringSystem, List<String> fields, List<String> majors, String introduction) {
        this.id = id;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.preferredMentoringSystem = preferredMentoringSystem;
        this.fields = fields;
        this.majors = majors;
        this.introduction = introduction;
    }
}
