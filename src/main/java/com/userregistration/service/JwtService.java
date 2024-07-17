package com.userregistration.service;

import com.userregistration.domain.entites.User;

public interface JwtService {
    String generateToken(User user);
    String getSubjectFromToken(String token);
}
