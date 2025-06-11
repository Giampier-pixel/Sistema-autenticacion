
package com.example.demo_jwt.Auth;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo_jwt.Jwt.JwtService;
import com.example.demo_jwt.User.Role;
import com.example.demo_jwt.User.User;
import com.example.demo_jwt.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    // Repositorio para operaciones con usuarios en BD
    private final JwtService jwtService;
    // Servicio para generar y validar tokens JWT
    private final PasswordEncoder passwordEncoder;
    // Codificador de contraseñas (BCrypt)
    private final AuthenticationManager authenticationManager;
    // Manager de Spring Security para autenticar usuarios
    public AuthResponse  login(LoginRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
         // Autentica al usuario usando Spring Security con username y password 
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
         // Busca el usuario en la BD, lanza excepción si no existe
        String token = jwtService.getToken(user);
        // Genera un token JWT para el usuario autenticado
        return AuthResponse.builder()
                .token(token)
                .build();
        // Retorna la respuesta con el token
    }
    public AuthResponse register(RegisterRequest request)
    {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                // Encripta la contraseña antes de guardarla
                .firstname(request.getFirstname())
                .lastname(request.lastname)
                .country(request.getCountry())
                .role(Role.USER)
                 // Asigna rol de usuario por defecto
                .build();

            userRepository.save(user);
             // Guarda el nuevo usuario en la base de datos
            return AuthResponse.builder().token(jwtService.getToken(user)).build();
            // Genera token JWT para el usuario recién registrado y lo retorna
    }
}
