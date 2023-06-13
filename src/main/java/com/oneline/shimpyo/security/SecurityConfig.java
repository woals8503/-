package com.oneline.shimpyo.security;


import com.oneline.shimpyo.security.filter.CustomAuthenticationFilter;
import com.oneline.shimpyo.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final UserDetailsService userDetailsService;

    // 만든 인증시스템인 CustomAuthenticationProvider를 ProviderManager가 알 수 있게 ProviderManager에게 등록해준다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//         login 전용 필터
        CustomAuthenticationFilter filter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

//      매번인증하지않고 필요한 경우만 토큰 만든다
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용 X

        http.authorizeRequests().antMatchers("/api/**").permitAll();
        http.authorizeRequests().antMatchers("/api/client/**").hasAnyAuthority("CLIENT");
        // 토큰이 아니라 그 회원이 가지고있는 Role
        http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().anyRequest().authenticated();   // 이외 uri 요청 시 인증 필요 -> 추후 변경 예정

        http.addFilter(filter);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        // usernamePassword 직전에 customAuthorization필터가 걸리도록 지정

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
