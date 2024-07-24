package com.app.contador.security;

//import com.app.contador.services.UserDetailsServiceImpl;
import com.app.contador.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//                .csrf(AbstractHttpConfigurer::disable)  // Desactiva CSRF para simplificar ejemplos. Ajusta según sea necesario.
                .authorizeHttpRequests((auth) ->
                        auth

                                .requestMatchers("api/v1/prueba").permitAll()
                                .requestMatchers("api/v1/carros").authenticated()
                                .anyRequest().authenticated()


//                                .requestMatchers("api/v1/carros").permitAll()

                );

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



//                .formLogin(formLogin ->
//                        formLogin
//                                .loginProcessingUrl(appUrl)
//                                .permitAll()  // Permite acceso a la página de login
//                )
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/api/logout")
//                                .permitAll()  // Permite acceso a la página de logout
//                );