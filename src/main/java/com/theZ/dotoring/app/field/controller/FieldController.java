package com.theZ.dotoring.app.field.controller;


import com.theZ.dotoring.app.field.dto.FieldResponseDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import com.theZ.dotoring.common.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FieldController {

    @GetMapping("/fields")
    public ApiResponse<ApiResponse.CustomBody<FieldResponseDTO>> findFields(){
        List<String> fields = Field.getFields().stream().map(f -> f.toString()).collect(Collectors.toList());
        FieldResponseDTO fieldResponseDTO = new FieldResponseDTO(fields);
        return ApiResponseGenerator.success(fieldResponseDTO, HttpStatus.OK);
    }
}
