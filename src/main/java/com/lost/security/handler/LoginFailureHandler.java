package com.lost.security.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lost.common.controller.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        log.info("[인증 실패] 400");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("아이디 혹은 비밀번호가 올바르지 않습니다.")
                .build();

        response.setStatus(400);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.print(objectMapper.writeValueAsString(errorResponse));
        writer.close();
    }
}
