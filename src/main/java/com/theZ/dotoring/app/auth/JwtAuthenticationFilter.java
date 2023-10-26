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
import java.util.Optional;


@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final MemberDetailService memberDetailService;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, MemberDetailService memberDetailService) {
        super(authenticationManager);
        this.memberDetailService = memberDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        Optional<String> accessToken = Optional.ofNullable(request.getHeader(AuthConstants.AUTH_HEADER));
        log.debug("accessToken, {}", accessToken.get());

        if(accessToken.isEmpty()){
            chain.doFilter(request,response);
            return;
        }

        boolean isValidAccessToken = Token.isValidAccessToken(accessToken.get(),response);

        if(isValidAccessToken){
            log.debug("isValidAccessToken, {}", true);
            Authentication authentication = memberDetailService.getAuthentication(accessToken.get());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }else{
            log.debug("isValidAccessToken, {}", false);
            Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
            Optional<String> refreshToken = Optional.ofNullable(getRefreshToken(cookies));
            if(refreshToken.isEmpty()){
                response.sendRedirect(request.getContextPath() + "/api/auth/reRequest");
            }else{
                boolean isValidRefreshToken = Token.isValidRefreshToken(refreshToken.get(),response);
                if(isValidRefreshToken){
                    log.debug("isValidRefreshToken, {}", true);
                    chain.doFilter(request,response);
                }else {
                    log.debug("isValidRefreshToken, {}", false);
                    response.sendRedirect(request.getContextPath() + "/api/auth/fail");
                }
            }

        }

    }

    private String getRefreshToken(Optional<Cookie[]> cookies){

        if(cookies.isEmpty()){
            return null;
        }

        for(Cookie cookie : cookies.get()) {
            if(cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
