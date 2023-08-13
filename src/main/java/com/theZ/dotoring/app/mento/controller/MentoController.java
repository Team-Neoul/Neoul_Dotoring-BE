package com.theZ.dotoring.app.mento.controller;

import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import com.theZ.dotoring.app.mento.handler.SaveMentoHandler;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentoController {

    private final SaveMentoHandler saveMentoHandler;

    @PostMapping("/signup-mento")
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMento(@RequestPart List<MultipartFile> certifications , @RequestPart @Valid MentoSignupRequestDTO mentoSignupRequestDTO) throws IOException {
        saveMentoHandler.execute(mentoSignupRequestDTO,certifications);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
