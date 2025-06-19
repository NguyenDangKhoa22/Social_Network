package com.example.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.ApiReponse;
import com.example.backend.dto.request.AuthenticationRequest;
import com.example.backend.dto.request.BlackListTokenRequest;
import com.example.backend.dto.request.IntroSpectRequest;
import com.example.backend.dto.request.RefreshTokenRequest;
import com.example.backend.dto.response.AuthenticationResponse;
import com.example.backend.dto.response.IntroSpectResponse;
import com.example.backend.service.UserAuthService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserAuthenticate {

    UserAuthService userAuthService;
    @PostMapping("/login")
    public ApiReponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = userAuthService.authenticate(request);
        log.info(request.getUsername());
        return ApiReponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    public ApiReponse<IntroSpectResponse> authenticate(@RequestBody IntroSpectRequest request)
            throws ParseException, JOSEException{
        var result = userAuthService.introSpect(request);
        return ApiReponse.<IntroSpectResponse>builder().result(result).build();
    }
    @PostMapping("/logout")
    public ApiReponse<Void> authenticate(@RequestBody BlackListTokenRequest request) throws JOSEException, ParseException{
        userAuthService.logout(request);
        return ApiReponse.<Void>builder().build();
    }
    @PostMapping("/refresh")
    public ApiReponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) 
        throws JOSEException, ParseException{
        var result = userAuthService.refreshToken(request);
        return ApiReponse.<AuthenticationResponse>builder().result(result).build();
    }
}
