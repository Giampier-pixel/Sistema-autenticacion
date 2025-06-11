package com.example.demo_jwt.User;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames ={"username"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Integer id;
     // Clave primaria auto-generada

    @Column(nullable = false)
    String username;
    // Campo username obligatorio
    String lastname;
    String firstname;
    String country;
    String password;
    // Campos adicionales del usuario
    @Enumerated(EnumType.STRING)
    Role role;
    // Rol del usuario almacenado como string
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
        // Retorna las autoridades del usuario basadas en su rol
    }
    @Override
public boolean isAccountNonExpired() {
    return true;
}

@Override
public boolean isAccountNonLocked() {
    return true;
}

@Override
public boolean isCredentialsNonExpired() {
    return true;
}

@Override
public boolean isEnabled() {
    return true;
}
public void setUsername(String username) {
    this.username = username;
}

public void setFirstname(String firstname) {
    this.firstname = firstname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}

public void setCountry(String country) {
    this.country = country;
}

public void setPassword(String password) {
    this.password = password;
}
}
