package com.example.inventory.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
	private String serverUrl;
	private String realm;
	private String adminClientId;
	private String adminClientSecret;
}
