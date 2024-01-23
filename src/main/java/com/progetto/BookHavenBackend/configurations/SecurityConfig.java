package com.progetto.BookHavenBackend.configurations;

import com.progetto.BookHavenBackend.support.authentication.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                    .anyRequest()
                         .authenticated(); //authenticate all the requests

        http
                .oauth2ResourceServer()
                    .jwt()  // i have a jwt that has to be validated using oauth2ResourceServer
                            //i need to add few parmeters or configurations to my application context in order to tell spring what is my resource server; url to validate token -> go to application.properties to add configs
                        .jwtAuthenticationConverter(jwtAuthConverter) // iw want to mention to psring that i want to use my own jwt converter instead of the default one
        ;
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();


    }
}
