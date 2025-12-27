package com.example.inventory.dto;

import java.util.List;

public record AdminUserResponse(String id, String username, Boolean enabled, List<String> roles) {
}
