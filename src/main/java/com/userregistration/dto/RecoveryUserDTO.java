package com.userregistration.dto;

import lombok.Builder;

@Builder
public record RecoveryUserDTO(
        String firstName,
        String lastName,
        String email
){}
