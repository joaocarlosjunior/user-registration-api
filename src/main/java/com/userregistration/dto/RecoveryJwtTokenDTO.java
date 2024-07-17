package com.userregistration.dto;

import lombok.Builder;

@Builder
public record RecoveryJwtTokenDTO(
        String token
) {
}
