package com.theZ.dotoring.app.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theZ.dotoring.app.auth.AuthConstants;
import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.auth.model.Token;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DotoringLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final Token token;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final MemberAccount memberAccount = ((MemberDetails) authentication.getPrincipal()).getMemberAccount();

        final String accessToken = token.generateAccessToken(memberAccount, Instant.now());
        final String refreshToken = token.generateRefreshToken(memberAccount, Instant.now());

        log.info("accessToken : " + accessToken);
        log.info("refreshToken : " + refreshToken);

        response.addHeader(AuthConstants.AUTH_HEADER,AuthConstants.TOKEN_TYPE + accessToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*7);// 쿠키에 만료 날짜를 주지 않으면 세션쿠키가 됨-> 세션 쿠키는 브라우저 종료시 삭제
        response.addCookie(cookie);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value(),HttpStatus.OK.toString());
        response.setContentType("application/json; charset=utf-8");
        ApiResponse.CustomBody customBody = new ApiResponse.CustomBody(true,null, null);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(customBody);
        response.getWriter().println(responseBody);
    }

}
