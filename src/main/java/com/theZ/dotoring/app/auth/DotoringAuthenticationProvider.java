package com.theZ.dotoring.app.auth;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.auth.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Slf4j
public class DotoringAuthenticationProvider implements AuthenticationProvider {


    private final MemberDetailService memberDetailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        final String memberLoginId = (String) token.getPrincipal();
        final String memberPassword = (String) token.getCredentials();

        // memberAccountService 통해 DB에서 아이디로 사용자 조회
        final MemberDetails memberDetails = (MemberDetails) memberDetailService.loadUserByUsername(memberLoginId);
        if (!passwordEncoder.matches(memberPassword, memberDetails.getPassword())) {

            log.info("RequestPassword : " +  memberPassword);
            log.info("storedPassword : " +  memberDetails.getPassword());

            throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
        }
        log.info("인증 완료 후 인증 토큰 생성");
        return new UsernamePasswordAuthenticationToken(memberDetails, memberPassword, memberDetails.getAuthorities());
    }



    /**
     * AuthenticatonManager가 supports를 통해서 해결할 수 있는 AuthentionProvider를 찾는다.
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
