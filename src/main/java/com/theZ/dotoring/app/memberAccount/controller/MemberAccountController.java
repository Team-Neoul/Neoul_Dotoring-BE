package com.theZ.dotoring.app.memberAccount.controller;

import com.theZ.dotoring.app.memberAccount.dto.*;
import com.theZ.dotoring.app.memberAccount.handler.FindMemberAccountHandler;
import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.memberAccount.service.MemberEmailService;
import com.theZ.dotoring.app.mento.dto.EmailCodeRequestDTO;
import com.theZ.dotoring.app.mento.dto.MemberPasswordRequestDTO;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "아이디 중복 검사", notes = "아이디는 영문과 숫자를 포함한 8~12글자여야 합니다.")
    @PostMapping("/member/valid-loginId")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberLoginId(@RequestBody @Valid MemberLoginIdRequestDTO memberLoginIdRequestDTO){
        memberAccountService.checkDuplicateAboutLoginId(memberLoginIdRequestDTO.getLoginId());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입시 인증 코드 발송")
    @GetMapping("/member/signup/code")
    public ApiResponse<ApiResponse.CustomBody<MemberEmailCodeResponseDTO>> sendEmailForSignUp(@Valid MemberEmailRequestDTO memberEmailRequestDTO) throws MessagingException {
        MemberEmailCodeResponseDTO memberEmailCodeResponseDTO = memberEmailService.sendEmailForSignup(memberEmailRequestDTO);
        return ApiResponseGenerator.success(memberEmailCodeResponseDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입시 인증 코드를 발송한 후에, 인증할 때 사용")
    @PostMapping("/member/signup/valid-code")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberEmailCodeForSignUp(@RequestBody @Valid MemberEmailCodeRequestDTO memberEmailCodeRequestDTO){
        memberEmailService.validateCodeForSignUp(memberEmailCodeRequestDTO.getEmailVerificationCode(),memberEmailCodeRequestDTO.getEmail());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "아이디 찾기 시, 해당 이메일에 인증코드 발송할 때 사용")
    @GetMapping("/member/code")
    public ApiResponse<ApiResponse.CustomBody<MemberEmailCodeResponseDTO>> sendEmail(@Valid MemberEmailRequestDTO memberEmailRequestDTO) throws MessagingException {
        MemberEmailCodeResponseDTO memberEmailCodeResponseDTO = memberEmailService.sendEmail(memberEmailRequestDTO);
        return ApiResponseGenerator.success(memberEmailCodeResponseDTO,HttpStatus.OK);
    }

    @ApiOperation(value = "아이디 찾기 시, 인증코드를 보낸 후에 인증하기 버튼을 누를 때 사용")
    @PostMapping("/member/valid-code")
    public ApiResponse<ApiResponse.CustomBody<Void>> validateMemberEmailCode(@RequestBody @Valid MemberEmailCodeRequestDTO memberEmailCodeRequestDTO){
        memberEmailService.validateCode(memberEmailCodeRequestDTO.getEmailVerificationCode(),memberEmailCodeRequestDTO.getEmail());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


    @ApiOperation(value = "아이디를 찾을 때 사용")
    @GetMapping("/member/loginId")
    public ApiResponse<ApiResponse.CustomBody<String>> findLoginId(@ModelAttribute @Valid EmailCodeRequestDTO emailCodeRequestDTO) {
        return ApiResponseGenerator.success(findMemberAccountHandler.executeForLoginId(emailCodeRequestDTO),HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호를 찾을 때 사용")
    @GetMapping("/member/password")
    public ApiResponse<ApiResponse.CustomBody<Void>> findPassword(@ModelAttribute @Valid MemberPasswordRequestDTO memberPasswordRequestDTO) throws MessagingException {
        findMemberAccountHandler.executeForPassword(memberPasswordRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "아이디를 수정할 때 사용")
    @PatchMapping("/member/loginId")
    public ApiResponse<ApiResponse.CustomBody<Void>> updateLoginId(@RequestBody UpdateMemberLoginIdRequestDTO updateMemberLoginIdRequestDTO){
        memberAccountService.updateLoginId(updateMemberLoginIdRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호를 찾을 때 사용")
    @PatchMapping("/member/password")
    public ApiResponse<ApiResponse.CustomBody<Void>> updatePassword(@RequestBody UpdateMemberPasswordRequestDTO updateMemberPasswordRequestDTO){
        memberAccountService.updatePassword(updateMemberPasswordRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


}
