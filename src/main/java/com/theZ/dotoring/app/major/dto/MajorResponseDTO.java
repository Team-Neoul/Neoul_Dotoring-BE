package com.theZ.dotoring.app.major.dto;

import lombok.Data;

import java.util.List;

@Data
public class MajorResponseDTO {

    private List<String> majors;

    public MajorResponseDTO(List<String> majors) {
        this.majors = majors;
    }
}
