package com.example.inventory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String API_INVENTORY = "/api/inventory/**";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, KeycloakRoleConverter roleConverter) throws Exception {

		JwtAuthConverter jwtAuthConverter = new JwtAuthConverter(roleConverter);
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/**").permitAll()
						.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers(HttpMethod.GET, API_INVENTORY).hasAnyAuthority(ROLE_ADMIN, "ROLE_USER")
						.requestMatchers("/api/admin/**").hasAuthority(ROLE_ADMIN)
						.requestMatchers(API_INVENTORY).hasAuthority(ROLE_ADMIN).anyRequest().authenticated())
				.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));

		return http.build();
	}
}
