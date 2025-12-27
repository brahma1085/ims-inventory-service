package com.example.inventory.service;

import com.example.inventory.dto.AdminUserResponse;
import com.example.inventory.dto.CreateUserRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public void createUser(CreateUserRequest request) {

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // 1️⃣ Create User
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEnabled(true);

        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }

        String userId = response.getLocation().getPath()
                .replaceAll(".*/([^/]+)$", "$1");

        // 2️⃣ Set Password
        CredentialRepresentation password = new CredentialRepresentation();
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(request.password());
        password.setTemporary(false);

        usersResource.get(userId).resetPassword(password);

        // 3️⃣ Assign Roles
        List<RoleRepresentation> realmRoles =
                realmResource.roles()
                        .list()
                        .stream()
                        .filter(r -> request.roles().contains(r.getName()))
                        .toList();

        usersResource.get(userId)
                .roles()
                .realmLevel()
                .add(realmRoles);
    }

    public List<AdminUserResponse> getUsers() {
        var realmResource = keycloak.realm(realm);
        Set<String> allowedRoles = Set.of(ADMIN, USER);
        return realmResource.users().list().stream()
                .map(user -> {
                    List<String> roles =
                            realmResource.users()
                                    .get(user.getId())
                                    .roles()
                                    .realmLevel()
                                    .listEffective()
                                    .stream()
                                    .map(RoleRepresentation::getName).filter(allowedRoles::contains)
                                    .toList();

                    return new AdminUserResponse(
                            user.getId(),
                            user.getUsername(),
                            user.isEnabled(),
                            roles
                    );
                })
                .toList();
    }

    public void updateRoles(String userId, List<String> roles) {
        var realmResource = keycloak.realm(realm);
        UserResource user = realmResource.users().get(userId);

        // Remove existing UI roles
        List<RoleRepresentation> existing = user.roles().realmLevel().listEffective();

        existing.stream()
                .filter(r -> r.getName().equals(ADMIN) || r.getName().equals(USER))
                .forEach(r -> user.roles().realmLevel().remove(List.of(r)));

        // Add new roles
        List<RoleRepresentation> toAdd = roles.stream()
                .map(r -> realmResource.roles().get(r).toRepresentation())
                .toList();
        user.roles().realmLevel().add(toAdd);
    }

    public void updatePassword(String userId, String password) {
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setTemporary(false);
        cred.setValue(password);
        UsersResource users = keycloak.realm(realm).users();
        users.get(userId).resetPassword(cred);
    }
}
