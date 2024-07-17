package com.userregistration.controller;

import com.userregistration.dto.LoginUserDTO;
import com.userregistration.dto.RecoveryJwtTokenDTO;
import com.userregistration.dto.RecoveryUserDTO;
import com.userregistration.dto.RegisterUserDTO;
import com.userregistration.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody RegisterUserDTO user, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        userService.signup(user, getSiteURL(request));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryJwtTokenDTO authenticateUser(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.authenticate(loginUserDTO);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@Param("code") String code) {
        return userService.verify(code);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}