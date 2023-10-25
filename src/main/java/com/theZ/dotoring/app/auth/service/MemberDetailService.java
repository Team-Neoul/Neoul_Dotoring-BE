package com.theZ.dotoring.app.auth.service;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.NotFoundMemberException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.theZ.dotoring.app.auth.model.Token.getClaimsFormToken;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberDetailService  implements UserDetailsService {

    private final MemberAccountRepository memberAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return memberAccountRepository.findByLoginId(loginId).map(m -> new MemberDetails(m, Collections.singleton(new SimpleGrantedAuthority(m.getMemberRole().getType())))).orElseThrow(() -> new NotFoundMemberException(MessageCode.MEMBER_NOT_FOUND));
    }

    // 토큰을 받아 클레임을 만들고 권한정보를 빼서 시큐리티 유저객체를 만들어 Authentication 객체 반환
    public Authentication getAuthentication(String jwt) {

        Claims claims = getClaimsFormToken(jwt);
        UserDetails memberDetails = loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(memberDetails,memberDetails.getPassword(), memberDetails.getAuthorities());
    }
}
