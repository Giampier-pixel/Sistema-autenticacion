package com.example.demo_jwt.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    String username;
    String password;
    String firstname;
    String lastname;
    String country;
    // Campos para recibir todos los datos del nuevo usuario
}


