package com.example.demo_jwt.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo_jwt.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
        // Configura el AuthenticationManager de Spring Security
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // Crea un proveedor de autenticación basado en DAO
        authenticationProvider.setUserDetailsService(userDetailsService());
        // Configura el servicio para cargar detalles del usuario
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // Configura el codificador de contraseñas
        return authenticationProvider;
    }


    @Bean
   public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Configura BCrypt como codificador de contraseñas
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username-> userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
                // Servicio que busca usuarios por username, lanza excepción si no existe
    }

}
