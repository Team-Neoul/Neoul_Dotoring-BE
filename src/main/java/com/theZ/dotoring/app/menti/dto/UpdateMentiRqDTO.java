package com.theZ.dotoring.app.menti.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateMentiRqDTO {

    private List<String> fields;
    private List<String> majors;
    private List<String> tags;
    private Long grade;
    private String school;
    private MultipartFile certificateFile;

}
