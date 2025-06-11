package com.example.demo_jwt.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController{

    private final AuthService authService;
    // Inyecta el servicio de autenticación
    @PostMapping(value ="login")
    // Mapea peticiones POST a /auth/login
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    // Recibe datos JSON del login y los convierte a LoginRequest
    {
        return ResponseEntity.ok(authService.login(request));
         // Llama al servicio de login y retorna respuesta HTTP 200 con el token
    }

    @PostMapping(value ="register")
    // Mapea peticiones POST a /auth/register
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    // Recibe datos JSON del registro y los convierte a RegisterRequest
    {
        return ResponseEntity.ok(authService.register(request));
        // Llama al servicio de registro y retorna respuesta HTTP 200 con el token
    }

}