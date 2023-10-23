package com.theZ.dotoring.app.menti.controller;

import com.theZ.dotoring.app.menti.dto.*;
import com.theZ.dotoring.app.menti.handler.FindAllMentiHandler;
import com.theZ.dotoring.app.menti.handler.SaveMentiHandler;
import com.theZ.dotoring.app.menti.handler.UpdateMentiDesiredFieldHandler;
import com.theZ.dotoring.app.menti.service.MentiService;
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
public class MentiController {

    private final SaveMentiHandler saveMentiHandler;
    private final MentiService mentiService;
    private final FindAllMentiHandler findAllMentiHandler;
    private final UpdateMentiDesiredFieldHandler updateMentiDesiredFieldHandler;

    @ApiOperation(value = "멘티 최종 회원가입때 사용")
    @PostMapping(value = "/signup-menti", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApiResponse.CustomBody<Void>> saveMenti(@ModelAttribute @Valid SaveMentiRqDTO saveMentiRqDTO) throws IOException {
        saveMentiHandler.execute(saveMentiRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 회원가입 시 닉네임 중복 검사할 때 사용", notes = "이름은 3자 이상 8자 이하로 입력해주세요.")
    @PostMapping("/menti/valid-nickname")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMentiNickname(@RequestBody @Valid ValidateMentiNicknameRqDTO validateMentiNicknameRqDTO){
        mentiService.validateNickname(validateMentiNicknameRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 홈에서 해당 멘티 상세 조회시 사용")
    @GetMapping("/menti/{id}")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> findMentiById(@PathVariable Long id){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.findMentiWithProfile(id);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "멘티 홈에서 도토링 추천 방식에따른 멘티들 추천")
    @GetMapping("/menti")
    public ApiResponse<ApiResponse.CustomBody<Slice<FindAllMentiRespDTO>>> findAllMentiBySlice(
            @RequestParam(required = false) Long lastMentiId, @RequestParam(defaultValue = "10") Integer size, Long mentoId){
        return ApiResponseGenerator.success(findAllMentiHandler.execute(lastMentiId, size, mentoId),HttpStatus.OK);
    }

    @GetMapping("/wait-menti")
    public ApiResponse<ApiResponse.CustomBody<Page<FindWaitMentiRespDTO>>> findWaitMentiByPage(
            @PageableDefault(size = 20) Pageable pageable
    ){
        return ApiResponseGenerator.success(mentiService.findWaitMentis(pageable),HttpStatus.OK);
    }

    @PatchMapping("/menti/status")
    public ApiResponse<ApiResponse.CustomBody<Void>> approveWaitMentis(@RequestBody ApproveWaitMentisRqDTO approveWaitMentisRqDTO){
        mentiService.approveWaitMentis(approveWaitMentisRqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 멘티 선호 멘토링 수정", notes = "선호 멘토링에 대해서 10글자 이상 300자 이하를 작성해야합니다.")
    @PatchMapping("/menti/preferredMentoring")
    public ApiResponse<ApiResponse.CustomBody<FindAllMentiRespDTO>> updateMentiMentoringSystem(@RequestBody @Valid UpdateMentiMentoringSystemRqDTO updateMentiMentoringSystemRqDTO){
        FindAllMentiRespDTO findAllMentiRespDTO = mentiService.updatePreferredMentoring(updateMentiMentoringSystemRqDTO);
        return ApiResponseGenerator.success(findAllMentiRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 멘티 소개 수정 ", notes = "10글자에서 100글자 사이로 입력해주세요")
    @PatchMapping("/menti/introduction")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateMentiIntroduction(@RequestBody @Valid UpdateMentiIntroductionRqDTO updateMentiIntroductionRqDTO){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updateItroduction(updateMentiIntroductionRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "마이페이지에서 멘티 닉네임 수정 ", notes = "이름은 3자 이상 8자 이하로 입력해주세요.")
    @PatchMapping("/menti/nickname")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateMentiNickname(@RequestBody @Valid UpdateMentiNicknameRqDTO updateMentiNicknameRqDTO){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updateNickname(updateMentiNicknameRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }


    @ApiOperation(value = "마이페이지에서 희망 멘토링 분야 수정 ")
    @PatchMapping("/menti/desiredField")
    public ApiResponse<ApiResponse.CustomBody<FindMentiByIdRespDTO>> updateMentiDesiredField(@RequestBody @Valid UpdateMentiDesiredFieldRqDTO updateMentiDesiredFieldRqDTO){
        FindMentiByIdRespDTO findMentiByIdRespDTO = updateMentiDesiredFieldHandler.execute(updateMentiDesiredFieldRqDTO);
        return ApiResponseGenerator.success(findMentiByIdRespDTO,HttpStatus.OK);
    }


    // 학과, 학년 및 학과 + 증명서

}

