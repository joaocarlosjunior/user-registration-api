package com.userregistration.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.userregistration.domain.entites.User;
import com.userregistration.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${api.jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        Instant now = Instant.now();
        long expiry = 36000L;//10 hours

        return JWT.create()
                .withIssuer("security-jwt-api")
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expiry))
                .withSubject(user.getUsername())
                .sign(algorithm);

    }

    public String getSubjectFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT
                .require(algorithm)
                .withIssuer("security-jwt-api")
                .build()
                .verify(token)
                .getSubject();
    }
}
