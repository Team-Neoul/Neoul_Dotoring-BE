package com.theZ.dotoring.app.menti.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateMentiIntroductionRqDTO {

    private Long mentiId;

    @Length(min = 10,max = 100)
    private String introduction;
}
