package com.example.backend.utils;

import java.text.ParseException;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;



@Component
public class JwtGetId {
    public Long getUserIdFromToken(String token, String secrecKey) throws ParseException, JOSEException{
        //Parse token
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier jwsVerifier = new MACVerifier(secrecKey.getBytes());
        if (!signedJWT.verify(jwsVerifier)) {
            throw new IllegalArgumentException("Invalid token");
        }

        //lấy claim từ payload
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        return claimsSet.getLongClaim("userId");
    }
    
}
