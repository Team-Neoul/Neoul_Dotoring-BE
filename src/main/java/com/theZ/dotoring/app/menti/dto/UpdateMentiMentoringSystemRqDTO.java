package com.theZ.dotoring.app.menti.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateMentiMentoringSystemRqDTO {

    private Long mentiId;

    @Size(min = 10, max = 300, message = "선호 멘토링에 대해서 10글자 이상 300자 이하를 작성해야합니다.")
    private String preferredMentoring;

}
