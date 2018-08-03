package com.thoughtworks.training.wukun.todoservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Component
public class JwtSignature implements Serializable {

    private final String secret = "abc";

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, getSecretKey())
                .compact();
    }


    public Integer getUserName(String token) {
        Claims claims = getClaimsFromToken(token);
        return Optional.ofNullable(claims.get("userId", Integer.class))
                .orElseThrow(() -> new UsernameNotFoundException("can't find user fron jwt token"));
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
    }

    private byte[] getSecretKey() {
        return secret.getBytes(Charset.forName("UTF-8"));
    }
}