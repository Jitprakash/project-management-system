package com.jitDev.projectmanagementsystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        String jwt = Jwts.builder().issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
        return jwt;
    }

    public static String getEmailFromToken(String jwt){

        jwt=jwt.substring(7).trim();


        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        String email = String.valueOf(claims.get("email"));

        return email;
    }


}
