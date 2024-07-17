package com.userregistration.domain.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER(0),
    ROLE_ADMIN(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Valor de Role inválido: " + value);
    }
}
