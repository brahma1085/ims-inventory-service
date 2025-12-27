package com.example.inventory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().components(new Components().addSecuritySchemes("keycloak", new SecurityScheme()
				.type(SecurityScheme.Type.OAUTH2)
				.flows(new OAuthFlows().authorizationCode(new OAuthFlow()
						.authorizationUrl(
								"http://localhost:8095/realms/microservices-realm/protocol/openid-connect/auth")
						.tokenUrl("http://localhost:8095/realms/microservices-realm/protocol/openid-connect/token")
						.scopes(new Scopes().addString("openid", "openid").addString("profile", "profile")
								.addString("email", "email"))))))
				.addSecurityItem(new SecurityRequirement().addList("keycloak"));
	}
}

/*
 * @Configuration public class OpenApiConfig {
 * 
 * @Value("${keycloak.auth-server-url}") private String authServerUrl;
 * 
 * @Value("${keycloak.realm}") private String realm;
 * 
 * @Value("${keycloak.client-id}") private String clientId;
 * 
 * @Bean public OpenAPI openAPI() {
 * 
 * String authorizationUrl = authServerUrl + "/realms/" + realm +
 * "/protocol/openid-connect/auth";
 * 
 * String tokenUrl = authServerUrl + "/realms/" + realm +
 * "/protocol/openid-connect/token";
 * 
 * OAuthFlow authorizationCodeFlow = new
 * OAuthFlow().authorizationUrl(authorizationUrl).tokenUrl(tokenUrl).scopes( new
 * Scopes().addString("openid", "OpenID").addString("profile",
 * "Profile").addString("email", "Email"));
 * 
 * SecurityScheme oauthScheme = new
 * SecurityScheme().type(SecurityScheme.Type.OAUTH2) .flows(new
 * OAuthFlows().authorizationCode(authorizationCodeFlow));
 * 
 * return new OpenAPI().components(new
 * Components().addSecuritySchemes("keycloak", oauthScheme))
 * .addSecurityItem(new SecurityRequirement().addList("keycloak")); } }
 */