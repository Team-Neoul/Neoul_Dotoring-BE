package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FindWaitMentiRespDTO {

    private String nickname;
    private String school;
    private Long grade;

    @Builder
    public FindWaitMentiRespDTO(String nickname, String school, Long grade) {
        this.nickname = nickname;
        this.school = school;
        this.grade = grade;
    }

}
