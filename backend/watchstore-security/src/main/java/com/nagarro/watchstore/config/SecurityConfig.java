package com.nagarro.watchstore.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.nagarro.watchstore.jwtservice.JwtAuthFilter;

/**
 * 
 * @author mdsharif,vishaldeswal,yogesh
 *
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	/* For Authentication */
	@Bean
	public UserDetailsService userDetailsService() {
		return new SpringUserDetailsService();
	}

	/**
	 *
	 * @return encrypted password
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * @implNote: This method is used to authenticate different paths
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()).cors(cors -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setMaxAge(3600L);
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
            corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

            cors.configurationSource(request -> corsConfiguration);
        })
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/watches/brand", "/watches").permitAll()
						.requestMatchers(HttpMethod.GET, "/watches/{modelNumber}").permitAll().requestMatchers(SWAGGGER)
						.permitAll().requestMatchers(HttpMethod.POST, "/users").permitAll().requestMatchers(AUTH_NEEDED)
						.authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	/**
	 * 
	 * @return authenticationProvider
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * API end points which are secured
	 */
	private static final String[] AUTH_NEEDED = { "/users/{id}", "/users/logout", "/orders/**", "/notifications/**",
			 "/carts/**","/addresses/**", "/watches/**" };

	/**
	 * Swagger API endpoints which dont need authorization
	 */
	private static final String[] SWAGGGER = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
			"/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
			"/swebjars/**", "swagger-ui/index.html", "swagger-ui/**", "/swagger-ui.html", "/v3/api-docs.yml", };
}
