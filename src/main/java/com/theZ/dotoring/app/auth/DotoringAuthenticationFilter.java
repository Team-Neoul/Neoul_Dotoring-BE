package com.theZ.dotoring.app.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.InputNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class DotoringAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public DotoringAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        final UsernamePasswordAuthenticationToken authenticationToken;

        try {
            MemberAccount memberAccount = new ObjectMapper().readValue(request.getInputStream(), MemberAccount.class);
            log.info("loginId : " + memberAccount.getLoginId());
            log.info("password : " + memberAccount.getPassword());
            authenticationToken = new UsernamePasswordAuthenticationToken(memberAccount.getLoginId(),memberAccount.getPassword());
        } catch (IOException e) {
            throw new InputNotFoundException(MessageCode.INPUT_NOT_FOUND);
        }

        log.info("principal : " + authenticationToken.getPrincipal());
        log.info("credential : " + authenticationToken.getCredentials());

        setDetails(request,authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

}
