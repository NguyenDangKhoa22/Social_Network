package com.example.backend.configurattion;

import java.text.ParseException;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.backend.dto.request.IntroSpectRequest;
import com.example.backend.service.UserAuthService;
import com.nimbusds.jose.JOSEException;

@Component
public class CustomDecoder implements JwtDecoder{

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private UserAuthService userAuthService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try{
           var response =  userAuthService.introSpect(IntroSpectRequest.builder().token(token).build());

           if (!(response.isValid())) 
            throw new JwtException("token invalid");
           
        }catch(JOSEException | ParseException e){
            throw new JwtException(e.getMessage());
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                            .macAlgorithm(MacAlgorithm.HS512)
                            .build();
        }
        System.out.println(nimbusJwtDecoder);

        return nimbusJwtDecoder.decode(token);
    }
    
}
