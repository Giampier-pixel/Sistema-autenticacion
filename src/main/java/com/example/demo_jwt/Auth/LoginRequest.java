package com.example.demo_jwt.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {
    String username;
    // Campo para recibir el nombre de usuario
    String password;
    // Campo para recibir la contraseña
}
