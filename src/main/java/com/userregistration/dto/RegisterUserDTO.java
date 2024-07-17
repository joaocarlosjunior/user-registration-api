package com.userregistration.dto;

public record RegisterUserDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        Integer role
) {}
