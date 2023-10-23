package com.theZ.dotoring.app.mento.controller;

import com.theZ.dotoring.app.menti.dto.UpdateMentoDesiredFieldRqDTO;
import com.theZ.dotoring.app.mento.dto.*;
import com.theZ.dotoring.app.mento.handler.FindAllMentoHandler;
import com.theZ.dotoring.app.mento.handler.SaveMentoHandler;
import com.theZ.dotoring.app.mento.handler.UpdateMentoDesiredFieldHandler;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentoController {

    private final SaveMentoHandler saveMentoHandler;
    private final FindAllMentoHandler findAllMentoHandler;
    private final MentoService mentoService;
    private final UpdateMentoDesiredFieldHandler updateMentoDesiredFieldHandler;

    @ApiOperation(value = "멘토 최종 회원가입때 사용")
    @PostMapping(value = "/signup-mento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMento(@ModelAttribute @Valid SaveMentoRqDTO saveMentoRqDTO) throws IOException {
        saveMentoHandler.execute(saveMentoRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 회원가입 시 닉네임 중복 검사할 때 사용", notes = "이름은 3자 이상 8자 이하로 입력해주세요.")
    @PostMapping("/mento/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMentoNickname(@RequestBody @Valid ValidateMentoNicknameRqDTO validateMentoNicknameRqDTO){
        mentoService.validateNickname(validateMentoNicknameRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 홈에서 도토링 추천 방식에따른 멘토들 추천")
    @GetMapping("/mento")
    public ApiResponse<ApiResponse.CustomBody<Slice<FindAllMentoRespDTO>>> findAllMentoBySlice(
            @RequestParam(required = false) Long lastMentoId, @RequestParam(defaultValue = "10") Integer size, Long mentiId){
        return ApiResponseGenerator.success(findAllMentoHandler.execute(lastMentoId, size, mentiId),HttpStatus.OK);
    }

    @GetMapping("/wait-mento")
    public ApiResponse<ApiResponse.CustomBody<Page<FindWaitMentoRespDTO>>> findWaitMentoByPage(
        @PageableDefault(size = 20) Pageable pageable
    ){
        return ApiResponseGenerator.success(mentoService.findWaitMentos(pageable),HttpStatus.OK);
    }

    @PatchMapping("/mento/status")
    public ApiResponse<ApiResponse.CustomBody<Void>> approveWaitMentos(@RequestBody ApproveWaitMentosRqDTO approveWaitMentosRqDTO){
        mentoService.approveWaitMentos(approveWaitMentosRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘토 홈에서 해당 멘토 상세 조회시 사용")
    @GetMapping("/mento/{id}")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> findMentoById(@PathVariable Long id){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.findMentoByProfile(id);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 멘토 멘토링 방식 수정")
    @PatchMapping ("/mento/mentoringSystem")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoMentoringSystem(@RequestBody @Valid UpdateMentoringSystemRqDTO updateMentoringSystemRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateMentoringSystem(updateMentoringSystemRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 멘토 소개 수정 ", notes = "10글자에서 100글자 사이로 입력해주세요")
    @PatchMapping("/mento/introduction")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoIntroduction(@RequestBody @Valid UpdateMentoIntroductionRqDTO updateMentoIntroductionRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateIntroduction(updateMentoIntroductionRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 멘토 닉네임 수정 ", notes = "이름은 3자 이상 8자 이하로 입력해주세요.")
    @PatchMapping("/mento/nickname")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoNickname(@RequestBody @Valid UpdateMentoNicknameRqDTO updateMentoNicknameRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateNickname(updateMentoNicknameRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 희망 멘토링 분야 수정 ")
    @PatchMapping("/mento/desiredField")
    public ApiResponse<ApiResponse.CustomBody<FindMentoByIdRespDTO>> updateMentoDesiredField(@RequestBody @Valid UpdateMentoDesiredFieldRqDTO updateMentoDesiredFieldRqDTO){
        FindMentoByIdRespDTO findMentoByIdRespDTO = updateMentoDesiredFieldHandler.execute(updateMentoDesiredFieldRqDTO);
        return ApiResponseGenerator.success(findMentoByIdRespDTO,HttpStatus.OK);
    }


    // 학과, 학년 및 학과 + 증명서



}
