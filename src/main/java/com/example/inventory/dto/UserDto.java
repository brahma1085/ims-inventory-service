package com.example.inventory.dto;

import java.util.List;

public record UserDto(String id, String username, List<String> roles) {
}

