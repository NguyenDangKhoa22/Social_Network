package com.example.backend.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.backend.dto.request.AuthenticationRequest;
import com.example.backend.dto.request.IntroSpectRequest;
import com.example.backend.dto.response.AuthenticationResponse;
import com.example.backend.dto.response.IntroSpectResponse;
import com.example.backend.entity.User;
import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

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
    @Value("${jwt.signerKey}")
    protected String SECRET_KEY ;


    public IntroSpectResponse introSpect(IntroSpectRequest request) throws JOSEException, ParseException{
            var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified =  signedJWT.verify(verifier);

        return IntroSpectResponse.builder().valid(verified && expiDate.after(new Date())).build();
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(()->new AppExeption(ErrorCode.USERNAME_NOT_EXITED));
        
        boolean authenlicated =  passwordEncoder.matches(request.getPassword(), user.getPassword());
        
        if (!authenlicated) {
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    } 
    
    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("localhost:8080")
                    .issueTime(new Date())
                    .expirationTime(new Date(Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()))
                    .claim("scope", builderScope(user))
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

    private String builderScope (User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRole()))
           user.getRole().forEach(
            role->{
                stringJoiner.add("ROLE_"+role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                role.getPermissions().forEach(
                    permission -> 
                    stringJoiner.add(permission.getName()));
                });
        
        return stringJoiner.toString();
    }
}
