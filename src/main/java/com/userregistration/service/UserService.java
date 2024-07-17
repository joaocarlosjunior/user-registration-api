package com.userregistration.service;

import com.userregistration.dto.LoginUserDTO;
import com.userregistration.dto.RecoveryJwtTokenDTO;
import com.userregistration.dto.RegisterUserDTO;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface UserService {
    void signup(RegisterUserDTO user, String siteUrl) throws MessagingException, UnsupportedEncodingException;
    RecoveryJwtTokenDTO authenticate(LoginUserDTO loginUser);

    ResponseEntity<?> verify(String verificationToken);
}
