package com.example.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.AuthenticationRequest;
import com.example.backend.dto.response.AuthenticationResponse;
import com.example.backend.service.UserAuthService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserAuthenticate {

    UserAuthService userAuthService;
    @PostMapping("/login")
    ApiReponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        boolean result = userAuthService.authenticated(request);
        return ApiReponse.<AuthenticationResponse>builder().result(AuthenticationResponse.builder().authenticated(result).build()).build();
    }
    
}
