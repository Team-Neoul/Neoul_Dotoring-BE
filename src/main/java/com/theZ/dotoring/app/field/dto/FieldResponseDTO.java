package com.theZ.dotoring.app.field.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldResponseDTO {

    private List<String> fields;

    public FieldResponseDTO(List<String> fields) {
        this.fields = fields;
    }
}
