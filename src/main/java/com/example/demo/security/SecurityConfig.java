package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Para APIs REST CSRF no aplica
            .csrf(csrf -> csrf.disable())

            // Sin sesión (JWT será stateless)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Reglas de acceso
            .authorizeHttpRequests(auth -> auth
                // PERMITIR sin autenticación
                .requestMatchers(
                    "/api/v1/usuarios",// GET lista
                    "/api/v1/usuarios/**",// por si hay más
                    "/api/v1/auth/**"// luego login
                ).permitAll()
                // todo lo demás protegido por JWT (token)
                .anyRequest().authenticated() 
            )

            // Deshabilitamos login por formulario y basic
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable());

            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}

/*
    new Argon2PasswordEncoder(
            16,   // salt length
            32,   // hash length
            1,    // parallelism
            1 << 16, // memory 64MB
            3     // iterations
    );

*/
