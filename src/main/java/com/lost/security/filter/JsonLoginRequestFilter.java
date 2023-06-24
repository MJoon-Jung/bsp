package com.lost.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JsonLoginRequestFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/api/auth/login", HttpMethod.POST.name());
    private final ObjectMapper objectMapper;

    public JsonLoginRequestFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (HttpMethod.valueOf(request.getMethod()) != HttpMethod.POST) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        Map<String, String> map = objectMapper.readValue(request.getInputStream(), Map.class);
        LoginRequest loginRequest = new LoginRequest(map.get("email"), map.get("password"));
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.email,
                loginRequest.password
        );

        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken token) {
        token.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private class LoginRequest {

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 6자 이상이어야 합니다.")
        @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
        private String password;

        public LoginRequest(@Valid String email, @Valid String password) {
            this.email = email;
            this.password = password;
        }
    }
}