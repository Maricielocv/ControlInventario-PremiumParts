package com.proyecto.sistema.inventario.security;

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

    private final CustomSuccessHandler customSuccessHandler;

    SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/",
                                "/login/**",
                                "/page",
                                "/catalogo/**",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/error")
                        .permitAll()
                        .requestMatchers("/empleados/**")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/inventario/**")
                        .hasAnyRole("ADMINISTRADOR", "ALMACENERO")
                        .requestMatchers("/ventas/**")
                        .hasAnyRole("ADMINISTRADOR", "VENDEDOR")
                        .anyRequest().authenticated())

                        .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login/recuperar", "/login/reset-password"))
                        
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
