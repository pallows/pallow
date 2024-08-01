package com.pallow.pallow.global.config;

import com.pallow.pallow.global.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable) // 특정 도메인 간 요청을 허용하기 위해 사용됌 정말 필요한지 확인
                .csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 기존 시큐리티의 세션방식을 사용하지 않음

        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(
                                "/auth/local/signup",
                                "/auth/local",
                                "/auth/email/**",
                                "/chat",
                                "/InvitationList",
                                "/login",
                                "/main",
                                "/meets",
                                "/meetsCollection",
                                "/register",
                                "/register_information",
                                "/userboard",
                                "/userboardCollection",
                                "/benefits",
                                "/payments",
                                "/profiles",
                                "/",
                                "/api/chat/**",
                                "/api/meets",
                                "/css/**",
                                "/js/**",
                                "/ws/**"
                        ).permitAll()
                        .anyRequest().authenticated()
        ).authenticationProvider(authenticationProvider);
//
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
