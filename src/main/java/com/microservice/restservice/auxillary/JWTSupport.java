package com.microservice.restservice.auxillary;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

// Exclude to separate module
public class JWTSupport {

    public Key getHmacKey(){
        return new SecretKeySpec(
                Base64.getDecoder().decode(SecretKeyStorage.getSecret()),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String name, String id) {
        return Jwts.builder()
                .claim("name", name)
                .setSubject("MySubject")
                .setId(id)
                .signWith(getHmacKey())
                .compact();
    }

    public boolean validateToken(String token, String name, String id) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getHmacKey())
                .build()
                .parseClaimsJws(token).getBody();
        String jwtName = (String) claims.get("name");
        String jwtId = claims.getId();
        return name.equals(jwtName) & id.equals(jwtId);
    }

}
