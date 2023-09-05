package com.theZ.dotoring.app.mento.controller;

import com.theZ.dotoring.app.menti.dto.UpdateMentoDesiredFieldRqDTO;
import com.theZ.dotoring.app.mento.dto.*;
import com.theZ.dotoring.app.mento.handler.FindAllMentoHandler;
import com.theZ.dotoring.app.mento.handler.SaveMentoHandler;
import com.theZ.dotoring.app.mento.handler.UpdateMentoDesiredFieldHandler;
import com.theZ.dotoring.app.mento.service.MentoService;
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
public class MentoController {

    private final SaveMentoHandler saveMentoHandler;
    private final FindAllMentoHandler findAllMentoHandler;
    private final MentoService mentoService;
    private final UpdateMentoDesiredFieldHandler updateMentoDesiredFieldHandler;

    @PostMapping("/signup-mento")
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMento(@RequestPart List<MultipartFile> certifications , @RequestPart @Valid SaveMentoRqDTO saveMentoRqDTO) throws IOException {
        saveMentoHandler.execute(saveMentoRqDTO,certifications);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/mento/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMentoNickname(@RequestBody @Valid ValidateMentoNicknameRqDTO validateMentoNicknameRqDTO){
        mentoService.validateNickname(validateMentoNicknameRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/mento")
    public ApiResponse<ApiResponse.CustomBody<Slice<FindAllMentoRespDTO>>> findAllMentoBySlice(
            @RequestParam(required = false) Long lastMentoId, @RequestParam(defaultValue = "10") Integer size, Long mentiId){
        return ApiResponseGenerator.success(findAllMentoHandler.execute(lastMentoId, size, mentiId),HttpStatus.OK);
    }
    @GetMapping("/mento/{id}")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> findMentoById(@PathVariable Long id){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.findMentoByProfile(id);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    @PatchMapping ("/mento/mentoringSystem")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoMentoringSystem(@RequestBody @Valid UpdateMentoringSystemRqDTO updateMentoringSystemRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateMentoringSystem(updateMentoringSystemRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    @PatchMapping("/mento/introduction")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoIntroduction(@RequestBody @Valid UpdateMentoIntroductionRqDTO updateMentoIntroductionRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateIntroduction(updateMentoIntroductionRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    // 닉네임 수정
    @PatchMapping("/mento/nickname")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoNickname(@RequestBody @Valid UpdateMentoNicknameRqDTO updateMentoNicknameRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateNickname(updateMentoNicknameRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    // 희망 멘토링 분야
    @PatchMapping("/mento/desiredField")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoDesiredField(@RequestBody @Valid UpdateMentoDesiredFieldRqDTO updateMentoDesiredFieldRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = updateMentoDesiredFieldHandler.execute(updateMentoDesiredFieldRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }


    // 학과, 학년 및 학과 + 증명서



}
