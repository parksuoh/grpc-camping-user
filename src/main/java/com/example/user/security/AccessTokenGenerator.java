package com.example.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.user.domains.Role;
import com.example.user.dtos.AuthInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class AccessTokenGenerator {
    private final Algorithm algorithm;

    public AccessTokenGenerator(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generate(String name, Role role) {
        return JWT.create()
                .withClaim("name", name)
                .withClaim("role", role.toString())
                .withExpiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    public boolean verify(String accessToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(accessToken);

            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public AuthInfoDto getTokenInfo(String accessToken) {
        String name = JWT.decode(accessToken).getClaim("name").asString();
        String role = JWT.decode(accessToken).getClaim("role").asString();
        return new AuthInfoDto(name, role);
    }

}