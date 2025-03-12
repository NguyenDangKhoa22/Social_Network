package com.example.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.backend.dto.request.AuthenticationRequest;

import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAuthService {
    UserRepository userRepository;
    
    public boolean authenticated(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new AppExeption(ErrorCode.USERNAME_NOT_EXITED));
        
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    } 
    
}
