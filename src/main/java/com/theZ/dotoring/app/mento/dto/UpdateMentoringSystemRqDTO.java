package com.theZ.dotoring.app.mento.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateMentoringSystemRqDTO {

    @Size(min = 10, max = 300, message = "멘토링 방식은 10글자 이상 300자 이하를 작성해야합니다.")
    private String mentoringSystem;

}
