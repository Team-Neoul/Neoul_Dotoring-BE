package com.theZ.dotoring.app.menti.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateMentoDesiredFieldRqDTO {

    private Long mentoId;
    private List<String> desiredFields;
}
