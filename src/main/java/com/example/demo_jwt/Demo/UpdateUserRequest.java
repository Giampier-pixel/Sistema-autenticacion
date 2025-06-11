package com.example.demo_jwt.Demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    String username;
    String password; // Opcional para actualización
    String firstname;
    String lastname;
    String country;
}