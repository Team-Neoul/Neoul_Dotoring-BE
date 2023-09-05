package com.theZ.dotoring.app.menti.controller;

import com.theZ.dotoring.app.menti.dto.*;
import com.theZ.dotoring.app.menti.handler.FindAllMentiHandler;
import com.theZ.dotoring.app.menti.handler.SaveMentiHandler;
import com.theZ.dotoring.app.menti.handler.UpdateMentiDesiredFieldHandler;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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
    private final FindAllMentiHandler findAllMentiHandler;
    private final UpdateMentiDesiredFieldHandler updateMentiDesiredFieldHandler;

    @PostMapping("/signup-menti")
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMenti(@RequestPart List<MultipartFile> certifications , @RequestPart @Valid SaveMentiRqDTO saveMentiRqDTO) throws IOException {
        saveMentiHandler.execute(saveMentiRqDTO,certifications);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/menti/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMentiNickname(@RequestBody @Valid ValidateMentiNicknameRqDTO validateMentiNicknameRqDTO){
        mentiService.validateNickname(validateMentiNicknameRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/menti/{id}")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> findMentiById(@PathVariable Long id){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.findMentiWithProfile(id);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }

    @GetMapping("/menti")
    public ApiResponse<ApiResponse.CustomBody<Slice<FindAllMentiRespDTO>>> findAllMentiBySlice(
            @RequestParam(required = false) Long lastMentiId, @RequestParam(defaultValue = "10") Integer size, Long mentoId){
        return ApiResponseGenerator.success(findAllMentiHandler.execute(lastMentiId, size, mentoId),HttpStatus.OK);
    }

    @PatchMapping("/menti/preferredMentoring")
    public ApiResponse<ApiResponse.CustomBody<FindAllMentiRespDTO>> updateMentiMentoringSystem(@RequestBody @Valid UpdateMentiMentoringSystemRqDTO updateMentiMentoringSystemRqDTO){
        FindAllMentiRespDTO findAllMentiRespDTO = mentiService.updatePreferredMentoring(updateMentiMentoringSystemRqDTO);
        return ApiResponseGenerator.success(findAllMentiRespDTO,HttpStatus.OK);
    }

    @PatchMapping("/menti/introduction")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateMentiIntroduction(@RequestBody @Valid UpdateMentiIntroductionRqDTO updateMentiIntroductionRqDTO){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updateItroduction(updateMentiIntroductionRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }

    // 닉네임 수정
    @PatchMapping("/menti/nickname")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateMentiNickname(@RequestBody @Valid UpdateMentiNicknameRqDTO updateMentiNicknameRqDTO){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updateNickname(updateMentiNicknameRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }


    // 희망 멘토링 분야
    @PatchMapping("/menti/desiredField")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateMentiDesiredField(@RequestBody @Valid UpdateMentiDesiredFieldRqDTO updateMentiDesiredFieldRqDTO){
        FindMentiByIdRespDTO findMentiByIdRespDTO = updateMentiDesiredFieldHandler.execute(updateMentiDesiredFieldRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }


    // 학과, 학년 및 학과 + 증명서

}

