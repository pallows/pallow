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
        http
                .cors(AbstractHttpConfigurer::disable) // 특정 도메인 간 요청을 허용하기 위해 사용됌 정말 필요한지 확인
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 기존 시큐리티의 세션방식을 사용하지 않음.
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(
                                "/auth/signup",
                                "/login",
                                "/email/**",
                                "/",
                                "/css/**",
                                "/js/**",
                                "/ws/**",
                                "/auth/email/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/main.html");
                        })
                        .failureHandler((request, response, exception) -> {
                            String errorMessage = "Invalid username or password";
                            request.getSession().setAttribute("error", errorMessage);
                            response.sendRedirect("/login.html?error");
                        })
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

/**
 * .formLogin() 설정을 추가하여 폼 기반 로그인을 구성합니다. loginPage("/login")로 커스텀 로그인 페이지를 지정합니다.
 * loginProcessingUrl("/auth/local")로 로그인 처리 URL을 설정합니다. successHandler를 사용하여 로그인 성공 시 main.html로
 * 리다이렉트합니다. failureHandler를 사용하여 로그인 실패 시 에러 메시지를 세션에 저장하고 로그인 페이지로 리다이렉트합니다.
 */