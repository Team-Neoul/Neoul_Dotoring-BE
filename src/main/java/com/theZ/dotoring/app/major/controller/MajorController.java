package com.theZ.dotoring.app.major.controller;

import com.theZ.dotoring.app.major.dto.MajorResponseDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import com.theZ.dotoring.common.Major;
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
public class MajorController {

    @GetMapping("/majors")
    public ApiResponse<ApiResponse.CustomBody<MajorResponseDTO>> findMajors(){
        List<String> majors = Major.getMajors().stream().map(m -> m.toString()).collect(Collectors.toList());
        MajorResponseDTO majorResponseDTO = new MajorResponseDTO(majors);
        return ApiResponseGenerator.success(majorResponseDTO, HttpStatus.OK);
    }


}
