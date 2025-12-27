package com.example.inventory.security;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class KeycloakAdminConfig {

	private final KeycloakProperties properties;

	@Bean
	public Keycloak keycloakAdmin() {
		return KeycloakBuilder.builder().serverUrl(properties.getServerUrl()).realm(properties.getRealm())
				.clientId(properties.getAdminClientId()).clientSecret(properties.getAdminClientSecret())
				.grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
	}
}
