package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // Spring Security 6.1 이상 권장 방식: 람다 기반 SecurityFilterChain

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())                          // CSRF 보호 비활성화 (API 서버일 경우)
                .formLogin(form -> form.disable())                    // 기본 로그인 폼 제거
                .httpBasic(httpBasic -> httpBasic.disable())          // Basic Auth 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()          // 회원가입, 로그인은 허용
                        .anyRequest().authenticated()                     // 나머지는 인증 필요
                )
                .build();
    }
}

