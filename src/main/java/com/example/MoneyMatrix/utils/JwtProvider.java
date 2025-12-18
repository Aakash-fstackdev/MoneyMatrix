package com.example.MoneyMatrix.utils;

import com.example.MoneyMatrix.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${secret}")
    private String secret;
    @Value("${expiry}")
    private int expiry;

    private SecretKey getSigning(){
        byte[] bytes= Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(CustomUserDetails userDetails){
        return Jwts.builder().signWith(getSigning()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiry)).subject(userDetails.getUsername()).claim("id",userDetails.getId()).compact();
    }

    private Claims claims(String token){
        return Jwts.parser().verifyWith(getSigning()).build().parseSignedClaims(token).getPayload();
    }

    public Long extractId(String token){
        return claims(token).get("id",Long.class);
    }

    public String extractEmail(String token){
       return claims(token).getSubject();
    }

    public Date extractExpiry(String token){
        return claims(token).getExpiration();
    }
    public boolean isTokenValid(String token){
        return extractExpiry(token).after(new Date());
    }
}
