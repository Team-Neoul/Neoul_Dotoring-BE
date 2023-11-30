package com.theZ.dotoring.app.auth.model;

import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class MemberDetails implements UserDetails {

    // 한 객체의 메서드를 다른 객체로 위임 시켜준다. -> MEMBER의 메서드를 MemberDetails로 위임 시켜줌
    @Delegate
    private final MemberAccount memberAccount;


    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return memberAccount.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
