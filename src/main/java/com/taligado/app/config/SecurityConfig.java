package com.taligado.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/register", "/logout").permitAll()
                        .requestMatchers("/control","/devices", "/branch-offices")
                        .hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/delete-account").hasAuthority("ADMIN")
                        .anyRequest().authenticated())

                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/control", true)
                        .failureUrl("/login?falha=true")
                        .permitAll())

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())

                .exceptionHandling((exception) ->
                        exception.accessDeniedHandler(
                                (request, response, AccessDeniedException) -> {
                                    response.sendRedirect("/access-denied");
                                }));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

