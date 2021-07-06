package com.ld.quicktest.models;

import org.springframework.security.core.GrantedAuthority;

// Enum Role, содержит возможные варианты ролей пользователей.

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
