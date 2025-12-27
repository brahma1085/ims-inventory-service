package com.example.inventory.dto;

import java.util.List;

public record RoleUpdateRequest(
        List<String> roles
) {
}
