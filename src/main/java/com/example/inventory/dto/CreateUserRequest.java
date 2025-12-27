package com.example.inventory.dto;

import java.util.List;

public record CreateUserRequest(
    String username,
    String password,
    List<String> roles
) {}
