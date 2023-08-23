package com.theZ.dotoring.app.mento.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class UpdateMentoIntroductionRqDTO {

    Long mentoId;

    @Length(min = 10,max = 100)
    private String introduction;

}
