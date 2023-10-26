package com.theZ.dotoring.config;

import com.theZ.dotoring.app.auth.DotoringAuthenticationFilter;
import com.theZ.dotoring.app.auth.DotoringAuthenticationProvider;
import com.theZ.dotoring.app.auth.FilterResponseUtils;
import com.theZ.dotoring.app.auth.JwtAuthenticationFilter;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DotoringLoginSuccessHandler dotoringLoginSuccessHandler;
    private final MemberDetailService memberDetailService;

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
                    .antMatchers("/api/fields","/api/majors","/api/member/code","/api/member/loginId","/api/member/password",
                            "/api/member/signup/code","/api/member/signup/valid-code","/api/member/valid-code","/api/member/valid-loginId",
                            "/api/menti/valid-nickname","/api/mento/valid-nickname","/api/member/login","/api/signup-menti","/api/signup-mento").permitAll()
                    .antMatchers("/api/menti/{id}","/api/mento/desiredField","/api/mento/introduction","/api/mento/mentoringSystem","/api/mento/nickname","/api/profile","/api/menti").hasRole("MENTO")
                    .antMatchers("/api/mento/{id}","/api/menti/desiredField","/api/menti/introduction","/api/menti/mentoringSystem","/api/menti/nickname","/api/profile","/api/mento").hasRole("MENTI");

        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/h2-console/*").permitAll();

        http.addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);

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

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(),memberDetailService);
        jwtAuthenticationFilter.afterPropertiesSet();
        return jwtAuthenticationFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(dotoringAuthenticationProvider());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT","PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
