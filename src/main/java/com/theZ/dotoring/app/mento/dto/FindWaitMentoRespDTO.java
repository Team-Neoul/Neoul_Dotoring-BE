package com.theZ.dotoring.app.mento.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FindWaitMentoRespDTO {

    private String nickname;
    private String school;
    private Long grade;

    @Builder
    public FindWaitMentoRespDTO(String nickname, String school, Long grade) {
        this.nickname = nickname;
        this.school = school;
        this.grade = grade;
    }
}
