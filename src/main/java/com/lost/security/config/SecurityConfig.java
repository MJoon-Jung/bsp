package com.lost.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lost.common.domain.EnvProperties;
import com.lost.security.filter.JsonLoginRequestFilter;
import com.lost.security.handler.LoginFailureHandler;
import com.lost.security.handler.LoginSuccessHandler;
import com.lost.security.userdetails.UserDetailsServiceImpl;
import com.lost.user.service.UserService;
import com.lost.user.service.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String SESSION_NAME = "JSESSIONID";
    private final UserService userService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final EnvProperties envProperties;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                        .requestMatchers("/api/users/duplicate/check").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(corsFilter())
                .addFilterBefore(jsonLoginRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(config -> config.logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout", "POST"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies(SESSION_NAME)
                        .logoutSuccessHandler(logoutSuccessHandler()))
                .build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new com.lost.security.handler.LogoutSuccessHandler(objectMapper);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(envProperties.getClientUrl());
        config.addAllowedHeader("*");           // 모든 header에 응답 허용
        config.addAllowedMethod("*");           // 모든 post,get,put,delete,patch 요청허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public JsonLoginRequestFilter jsonLoginRequestFilter() {
        JsonLoginRequestFilter filter = new JsonLoginRequestFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(userService, objectMapper));
        filter.setAuthenticationFailureHandler(new LoginFailureHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
