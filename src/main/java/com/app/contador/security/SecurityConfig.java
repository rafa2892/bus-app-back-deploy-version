package com.app.contador.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.url}")
    private String appUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("INTERCEPCIOOON");

        http
                .csrf(csrf -> csrf.disable())  // Desactiva CSRF para simplificar ejemplos. Ajusta según sea necesario.
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/**").permitAll()  // Permite acceso sin autenticación a rutas específicas
                                .anyRequest().authenticated()  // Requiere autenticación para todas las demás rutas
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginProcessingUrl(appUrl)
                                .permitAll()  // Permite acceso a la página de login
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/api/logout")
                                .permitAll()  // Permite acceso a la página de logout
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
