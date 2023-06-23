package com.lost.auth.controller;

import com.lost.auth.controller.request.SignUpRequest;
import com.lost.auth.service.UserSignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/auth/signup")
    public void signup(@Valid @RequestBody SignUpRequest request) {
        userSignUpService.signUp(request);
    }
}
