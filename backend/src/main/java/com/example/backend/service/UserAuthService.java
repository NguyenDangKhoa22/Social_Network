package com.example.backend.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.backend.dto.request.AuthenticationRequest;
import com.example.backend.dto.request.BlackListTokenRequest;
import com.example.backend.dto.request.IntroSpectRequest;
import com.example.backend.dto.request.RefreshTokenRequest;
import com.example.backend.dto.response.AuthenticationResponse;
import com.example.backend.dto.response.IntroSpectResponse;
import com.example.backend.entity.BlackListToken;
import com.example.backend.entity.User;
import com.example.backend.exeption.AppExeption;
import com.example.backend.exeption.ErrorCode;
import com.example.backend.repository.BlackListTokenRepository;
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
    BlackListTokenRepository blackListTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SECRET_KEY ;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;


    public IntroSpectResponse introSpect(IntroSpectRequest request) throws JOSEException, ParseException{
        var token = request.getToken();
        boolean isValid = true;
        try{
            verifyToken(token,false);
        }catch (AppExeption e){
            isValid = false;
        }

        return IntroSpectResponse.builder().valid(isValid).build();
    }

    private SignedJWT verifyToken(String token, boolean refresh) throws JOSEException, ParseException{
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiDate = (refresh) 
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION,ChronoUnit.HOURS).toEpochMilli()) 
                :signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified =  signedJWT.verify(verifier);
         if(!(verified && expiDate.after(new Date())))
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);

        if(blackListTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppExeption(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    } 

    public String getStringId(String token) throws ParseException{
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String userId = claims.getStringClaim("userId");
        return userId;
    }
    public void logout(BlackListTokenRequest request) throws JOSEException, ParseException{
        try {
            var signToken = verifyToken(request.getToken(),true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiry = signToken.getJWTClaimsSet().getExpirationTime();

            BlackListToken blackListToken = BlackListToken.builder().expiryTime(expiry).token(jit).build();
            blackListTokenRepository.save(blackListToken);
        } catch (AppExeption e) {
            log.info("Token aldready expired");
        }
        
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) 
        throws JOSEException, ParseException{
        var signedJWT = verifyToken(request.getToken(),true);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        BlackListToken blackListToken = BlackListToken.builder().expiryTime(expiryTime).token(jit).build();
        blackListTokenRepository.save(blackListToken);
        
        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(username).orElseThrow(
            ()-> new AppExeption(ErrorCode.USERID_NOT_EXITTED)
            );


        var token =  generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
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
                    .expirationTime(new Date(Instant.now().plus(VALID_DURATION,ChronoUnit.HOURS).toEpochMilli()))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("userId", user.getId())
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
        if(!CollectionUtils.isEmpty(user.getRoles()))
           user.getRoles().forEach(
            role->{
                stringJoiner.add("ROLE_"+role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                role.getPermissions().forEach(
                    permission -> 
                    stringJoiner.add(permission.getName()));
                });
        
        return stringJoiner.toString();
    }
    @Scheduled(cron = "0 0 * * * *")
    public void clearnUpExpiredToken(){
        blackListTokenRepository.deleteExpiredToken(new Date());
        System.out.println("Expired token clearn at: "+LocalDateTime.now());
    }

}
