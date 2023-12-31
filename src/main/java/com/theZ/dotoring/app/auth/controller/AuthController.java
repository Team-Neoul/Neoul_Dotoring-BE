package com.theZ.dotoring.app.auth.controller;

import com.theZ.dotoring.app.auth.AuthConstants;
import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.auth.model.Token;
import com.theZ.dotoring.app.auth.service.MemberDetailService;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final Token token;
    private final MemberDetailService memberDetailService;

    @PostMapping("/auth/reIssue")
    public ApiResponse<ApiResponse.CustomBody<Void>> reissueTokens(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findAny().orElseThrow(() -> new IllegalStateException("refreshToken 이라는 이름을 가진 cookie가 없습니다.")).getValue();
        String loginIdFromRefreshToken = token.getSubjectFormToken(refreshToken);
        UserDetails userDetails = memberDetailService.loadUserByUsername(loginIdFromRefreshToken);
        MemberDetails memberDetails = (MemberDetails) userDetails;
        String newAccessToken = token.generateAccessToken(memberDetails.getMemberAccount(), Instant.now());
        String newRefreshToken = token.generateRefreshToken(memberDetails.getMemberAccount(), Instant.now());
        setResponse(response, newAccessToken, newRefreshToken);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


    private void setResponse(HttpServletResponse response, String newAccessToken, String newRefreshToken) {
        response.addHeader(AuthConstants.AUTH_HEADER,AuthConstants.TOKEN_TYPE + newAccessToken);
        Cookie cookie = new Cookie("refreshToken", newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*7);
        // 쿠키에 만료 날짜를 주지 않으면 세션쿠키가 됨-> 세션 쿠키는 브라우저 종료시 삭제
        response.addCookie(cookie);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

}
