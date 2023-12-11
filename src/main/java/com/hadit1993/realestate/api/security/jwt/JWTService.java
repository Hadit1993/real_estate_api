package com.hadit1993.realestate.api.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTService {
    private final KeyPair keyPair;

    public JWTService(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public String generateToken(String username, List<String> authorities) {

        var claims = Jwts.claims(Map.of("authorities", authorities)).setSubject(username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setExpiration(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)))
                .compact();
    }
    public Claims verify(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token).getBody();
    }
}




