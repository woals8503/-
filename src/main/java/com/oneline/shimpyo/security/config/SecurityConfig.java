package com.oneline.shimpyo.security.config;

import com.oneline.shimpyo.security.filter.CustomAuthenticationFilter;
import com.oneline.shimpyo.security.filter.CustomAuthorizationFilter;
import com.oneline.shimpyo.security.oAuth.PrincipalOauth2UserService;
import com.oneline.shimpyo.security.oAuth.handler.OAuth2SuccessHandler;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Client
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationFailureHandler authenticationFailureHandler;    // 구현체의 CustomFailureHandler 작동
    private final AuthenticationSuccessHandler authenticationSuccessHandler;    // 구현체의 CustomSuccessHandler 작동
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final OAuth2SuccessHandler successHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // login 필터
        CustomAuthenticationFilter filter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        filter.setFilterProcessesUrl("/public/login");
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

        http
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .sessionManagement().sessionCreationPolicy(STATELESS);
        http.cors().configurationSource(corsConfigurationSource());

        http.authorizeRequests().antMatchers("/public/**").permitAll();    // api/public 붙으면 토큰 인증 X
        http.authorizeRequests().antMatchers("/oauth2/**").permitAll();
        http.authorizeRequests().antMatchers("/api/**").authenticated();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(filter);

        http.oauth2Login().successHandler(successHandler).userInfoEndpoint().userService(principalOauth2UserService);

        http.addFilterBefore(customAuthorizationFilter, BasicAuthenticationFilter.class);

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
