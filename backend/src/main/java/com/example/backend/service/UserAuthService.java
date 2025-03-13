package com.example.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.backend.dto.request.AuthenticationRequest;
import com.example.backend.dto.response.AuthenticationResponse;
import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAuthService {
    UserRepository userRepository;
    @NonFinal
    private static final String SECRET_KEY = "PFZeIvGj7yTnDDENrhozx7k9Z3j1v7tJDdG75bSj2wxeOltGqAJmUGmdd0Dc7xF1";
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(()->new AppExeption(ErrorCode.USERNAME_NOT_EXITED));
        
        boolean authenlicated =  passwordEncoder.matches(request.getPassword(), user.getPassword());
        
        if (!authenlicated) {
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUsername());

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    } 
    
    private String generateToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("localhost:8080")
                    .issueTime(new Date())
                    .expirationTime(new Date(Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()))
                    .claim("customeClaim", "Custom")
                    .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            
        } catch (JOSEException e) {
            log.error("Cannot create token",e);
            e.printStackTrace();
        }
        return jwsObject.serialize();
    }
}
