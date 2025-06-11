package com.example.demo_jwt.Demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    String username;
    String password;
    String firstname;
    String lastname;
    String country;
}