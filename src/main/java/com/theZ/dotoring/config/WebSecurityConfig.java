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

    private static final String [] PERMIT_PATH_PATTERN = {
            "/api/fields","/api/majors","/api/member/code","/api/member/loginId","/api/member/password",
            "/api/member/signup/code","/api/member/signup/valid-code","/api/member/valid-code","/api/member/valid-loginId",
            "/api/member/login","/api/menti/valid-nickname","/api/signup-menti","/api/mento/valid-nickname","/api/signup-mento"
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedHandler((request, response, authException) -> {
            FilterResponseUtils.unAuthorized(response);
        });
        http.addFilterBefore(dotoringAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                // 인증 정보를 세션에 저장하지 않고, 상태가 없는(무상태) 웹 애플리케이션을 구성하기 위해 사용됩니다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()

                .authorizeRequests()//요청에 대한 인증/인가 규칙을 설정합니다. 즉, 특정한 URL 패턴에 대해 어떤 역할(Role)을 가진 사용자만 접근을 허용할지를 지정합니다.
                        .antMatchers("/api/menti","/api/menti/{id}","/api/mento/desiredField","/api/mento/introduction","/api/mento/mentoringSystem","/api/mento/nickname","/api/profile").hasRole("MENTO")
                        .antMatchers("/api/mento","/api/mento/{id}","/api/menti/desiredField","/api/menti/introduction","/api/menti/mentoringSystem","/api/menti/nickname","/api/profile").hasRole("MENTI")
                        .antMatchers(PERMIT_PATH_PATTERN).permitAll(); // PERMIT_PATH_PATTERN에 해당하는 URL은 모든 사용자에게 접근이 허용됩니다. 즉, 인증된 사용자든 아니든 상관없이 모두 접근할 수 있습니다.
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/h2-console/*").permitAll();

        //http.addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);

    }

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
