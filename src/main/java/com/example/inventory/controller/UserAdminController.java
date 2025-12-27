package com.example.inventory.controller;

import com.example.inventory.dto.AdminUserResponse;
import com.example.inventory.dto.CreateUserRequest;
import com.example.inventory.dto.PasswordUpdateRequest;
import com.example.inventory.dto.RoleUpdateRequest;
import com.example.inventory.service.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final KeycloakUserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/roles")
    public void updateRoles(
            @PathVariable String userId,
            @RequestBody RoleUpdateRequest request
    ) {
        userService.updateRoles(userId, request.roles());
    }

    @PutMapping("/{userId}/password")
    public void updatePassword(
            @PathVariable String userId,
            @RequestBody PasswordUpdateRequest request
    ) {
        userService.updatePassword(userId, request.newPassword());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminUserResponse> getUsers() {
        return userService.getUsers();
    }
}
