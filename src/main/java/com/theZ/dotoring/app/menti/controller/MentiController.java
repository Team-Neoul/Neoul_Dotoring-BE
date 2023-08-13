package com.theZ.dotoring.app.menti.controller;

import com.theZ.dotoring.app.menti.dto.MentiNicknameRequestDTO;
import com.theZ.dotoring.app.menti.dto.MentiSignupRequestDTO;
import com.theZ.dotoring.app.menti.handler.SaveMentiHandler;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.dto.MentoNicknameRequestDTO;
import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import com.theZ.dotoring.app.mento.handler.SaveMentoHandler;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentiController {

    private final SaveMentiHandler saveMentiHandler;
    private final MentiService mentiService;

    @PostMapping("/signup-menti")
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMento(@RequestPart List<MultipartFile> certifications , @RequestPart @Valid MentiSignupRequestDTO mentiSignupRequestDTO) throws IOException {
        saveMentiHandler.execute(mentiSignupRequestDTO,certifications);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/menti/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberNickname(@RequestBody @Valid MentiNicknameRequestDTO mentiNicknameRequestDTO){
        mentiService.validateNickname(mentiNicknameRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


}

