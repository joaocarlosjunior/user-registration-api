package com.userregistration.controller;

import com.userregistration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/authenticated-user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String authenticatedUser(){
        return "Usuario Autenticado";
    }


    @GetMapping("/authenticated-admin")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String authenticatedAdmin(){
        return "Admin Autenticado";
    }

}
