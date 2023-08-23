package com.theZ.dotoring.app.menti.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateMentiDesiredFieldRqDTO {


    private Long mentiId;
    private List<String> desiredFields;
}
