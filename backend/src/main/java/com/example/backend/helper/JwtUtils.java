package com.example.backend.helper;

import java.text.ParseException;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtils {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SECRET_KEY;

    public JWTClaimsSet getClaims(String token) throws ParseException, JOSEException{
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(new MACVerifier(SECRET_KEY.getBytes()));
        if(!verified){
            throw new RuntimeException("Invalid JWT signature");
        }
        return signedJWT.getJWTClaimsSet();
    }

    public boolean isTokenExpired(JWTClaimsSet claims){
        Date expirationTime = claims.getExpirationTime();
        return expirationTime.before(expirationTime);
    }

    public String getUserName(String token) throws ParseException, JOSEException{
        return getClaims(token).getSubject();
    }

    public Long getUserId(String token) throws ParseException, JOSEException {
        return getClaims(token).getLongClaim("userId");
    }
    public String getScope(String token) throws ParseException, JOSEException {
        return getClaims(token).getStringClaim("scope");
    }
}
