package com.theZ.dotoring.app.memberAccount.controller;

import com.theZ.dotoring.app.memberAccount.dto.*;
import com.theZ.dotoring.app.memberAccount.handler.FindMemberAccountHandler;
import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.memberAccount.service.MemberEmailService;
import com.theZ.dotoring.app.mento.dto.EmailCodeRequestDTO;
import com.theZ.dotoring.app.mento.dto.MemberPasswordRequestDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberAccountController {

    private final MemberAccountService memberAccountService;
    private final MemberEmailService memberEmailService;
    private final FindMemberAccountHandler findMemberAccountHandler;

    @PostMapping("/member/valid-loginId")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberLoginId(@RequestBody @Valid MemberLoginIdRequestDTO memberLoginIdRequestDTO){
        memberAccountService.checkDuplicateAboutLoginId(memberLoginIdRequestDTO.getLoginId());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/member/valid-code")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberEmailCode(@RequestBody @Valid MemberEmailCodeRequestDTO memberEmailCodeRequestDTO){
        memberEmailService.validateCode(memberEmailCodeRequestDTO.getEmailVerificationCode(),memberEmailCodeRequestDTO.getEmail());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/member/code")
    public ApiResponse<ApiResponse.CustomBody<MemberEmailCodeResponseDTO>> sendEmail(@Valid MemberEmailRequestDTO memberEmailRequestDTO) throws MessagingException {
        MemberEmailCodeResponseDTO memberEmailCodeResponseDTO = memberEmailService.sendEmail(memberEmailRequestDTO);
        return ApiResponseGenerator.success(memberEmailCodeResponseDTO,HttpStatus.OK);
    }


    @GetMapping("/member/loginId")
    public ApiResponse<ApiResponse.CustomBody<String>> findLoginId(@ModelAttribute @Valid EmailCodeRequestDTO emailCodeRequestDTO) {
        return ApiResponseGenerator.success(findMemberAccountHandler.executeForLoginId(emailCodeRequestDTO),HttpStatus.OK);
    }

    @GetMapping("/member/password")
    public ApiResponse<ApiResponse.CustomBody<Void>> findPassword(@ModelAttribute @Valid MemberPasswordRequestDTO memberPasswordRequestDTO) throws MessagingException {
        findMemberAccountHandler.executeForPassword(memberPasswordRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PatchMapping("/member/loginId")
    public ApiResponse<ApiResponse.CustomBody<Void>> updateLoginId(@RequestBody UpdateMemberLoginIdRequestDTO updateMemberLoginIdRequestDTO){
        memberAccountService.updateLoginId(updateMemberLoginIdRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PatchMapping("/member/password")
    public ApiResponse<ApiResponse.CustomBody<Void>> updatePassword(@RequestBody UpdateMemberPasswordRequestDTO updateMemberPasswordRequestDTO){
        memberAccountService.updatePassword(updateMemberPasswordRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     *  승인하기 기능 구현
     */

    

}
