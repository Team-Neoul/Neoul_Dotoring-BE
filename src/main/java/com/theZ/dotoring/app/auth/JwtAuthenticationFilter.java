package com.theZ.dotoring.app.auth;

import com.theZ.dotoring.app.auth.model.Token;
import com.theZ.dotoring.app.auth.service.MemberDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final MemberDetailService memberDetailService;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, MemberDetailService memberDetailService) {
        super(authenticationManager);
        this.memberDetailService = memberDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String accessToken = request.getHeader(AuthConstants.AUTH_HEADER);

        if(accessToken == null){
            chain.doFilter(request,response);
            return;
        }

        boolean isValidAccessToken = Token.isValidAccessToken(accessToken);

        if(isValidAccessToken){
            Authentication authentication = memberDetailService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }else{
            String refreshToken = getRefreshToken(request.getCookies());
            if(refreshToken.isEmpty()){
                response.sendRedirect(request.getContextPath() + "/api/auth/reRequest");
            }else{
                boolean isValidRefreshToken = Token.isValidRefreshToken(refreshToken);
                if(isValidRefreshToken){
                    chain.doFilter(request,response);
                }else {
                    response.sendRedirect(request.getContextPath() + "/api/auth/fail");
                }
            }

        }

    }

    private String getRefreshToken(Cookie[] cookies){
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
