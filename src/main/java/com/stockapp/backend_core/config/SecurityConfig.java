package com.stockapp.backend_core.config;

import org.springframework.http.HttpMethod;
import com.stockapp.backend_core.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/").permitAll()
                        
                        /*

                        .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()

                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")		//.permitAll()//.hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/locations").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/locations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/locations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/locations/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/items").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/items/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/items/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/items/**").hasRole("ADMIN")

                        .requestMatchers("/movements/**").authenticated()
                        .anyRequest().authenticated()
                        
                        */
                        
                        .anyRequest().permitAll()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
