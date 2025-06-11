package com.example.demo_jwt.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Agregar estos imports en SecurityConfig.java
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import com.example.demo_jwt.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurtyConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Crea configuración de CORS
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    // Permite peticiones desde cualquier origen
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // Permite estos métodos HTTP
    configuration.setAllowedHeaders(Arrays.asList("*"));
    // Permite todos los headers
    configuration.setAllowCredentials(true);
    // Permite el envío de credenciales
    configuration.setMaxAge(3600L);
    // Tiempo de cache para preflight requests
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    // Aplica configuración CORS a todas las rutas
    return source;
}
    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
{
    return http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
         // Habilita CORS con la configuración definida
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authRequest ->
            authRequest
            .requestMatchers("/auth/**", "/api/usuarios/**","/api/productos/**").permitAll()

                .anyRequest().authenticated()
        )
        .sessionManagement(sessionManagement ->
            sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authProvider)
        // Configura el proveedor de autenticación
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        // Agrega el filtro JWT antes del filtro de autenticación por username/password
        .build();
}


}