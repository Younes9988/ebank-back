package com.example.ebank_application.config;

import com.example.ebank_application.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // ==========================
    // MAIN SECURITY CONFIG
    // ==========================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})

                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // auth endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        //public registration (example)
                        .requestMatchers(HttpMethod.POST, "/api/customers").permitAll()
                        .requestMatchers("/api/customers/me").hasRole("CUSTOMER")
                        .requestMatchers("/api/customers/me/password").hasRole("CUSTOMER")
                        .requestMatchers("/api/accounts/me").hasRole("CUSTOMER")
                        .requestMatchers("/api/transactions/me").hasRole("CUSTOMER")
                        //AGENT only
                        .requestMatchers("/api/customers/**").hasRole("AGENT")
                        .requestMatchers("/api/accounts/**").hasRole("AGENT")

                        // GENT or CUSTOMER
                        .requestMatchers("/api/transactions/**")
                        .hasAnyRole("AGENT", "CUSTOMER")

                        // everything else
                        .anyRequest().authenticated()
                )

                // JWT FILTER
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ==========================
    // AUTH MANAGER (login)
    // ==========================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ==========================
    // CORS CONFIG
    // ==========================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false); // JWT → no cookies

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
