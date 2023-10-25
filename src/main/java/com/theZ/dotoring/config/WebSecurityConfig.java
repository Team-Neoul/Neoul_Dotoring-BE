package com.theZ.dotoring.config;

import com.theZ.dotoring.app.auth.DotoringAuthenticationFilter;
import com.theZ.dotoring.app.auth.DotoringAuthenticationProvider;
import com.theZ.dotoring.app.auth.FilterResponseUtils;
import com.theZ.dotoring.app.auth.handler.DotoringLoginSuccessHandler;
import com.theZ.dotoring.app.auth.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DotoringLoginSuccessHandler dotoringLoginSuccessHandler;
    private final MemberDetailService memberDetailService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DotoringAuthenticationFilter dotoringAuthenticationFilter() throws Exception {
        DotoringAuthenticationFilter dotoringAuthenticationFilter = new DotoringAuthenticationFilter(authenticationManager());
        dotoringAuthenticationFilter.setFilterProcessesUrl("/member/login");
        dotoringAuthenticationFilter.setAuthenticationSuccessHandler(dotoringLoginSuccessHandler);
        dotoringAuthenticationFilter.afterPropertiesSet();
        return dotoringAuthenticationFilter;
    }

    @Bean
    public DotoringAuthenticationProvider dotoringAuthenticationProvider() {
        return new DotoringAuthenticationProvider(memberDetailService, bCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(dotoringAuthenticationProvider());
    }

}
