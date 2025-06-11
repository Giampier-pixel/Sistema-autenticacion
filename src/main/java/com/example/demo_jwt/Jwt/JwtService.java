package com.example.demo_jwt.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Service
public class JwtService {

    private static final String SECRET_KEY="12345678901234567ADFEFFR1234567890123FEFE789012345FFEEF67890123456789";
    // Clave secreta para firmar los tokens JWT
    public String getToken(UserDetails user) {
        
        return getToken(new HashMap<>(), user);
        // Genera token sin claims adicionales
    }

    private String getToken(
            Map<String, Object> extraClaims,
            UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                // Establece claims adicionales
                .setSubject(user.getUsername())
                 // Establece el username como subject del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Establece fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                // Establece fecha de expiración (24 minutos)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                // Firma el token con la clave secreta usando HS256
                .compact();
                // Genera el token final
        
    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
         // Convierte la clave secreta en un objeto Key
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
        // Extrae el username del token
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
         // Valida que el username coincida y el token no haya expirado
    }

    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            // Configura la clave para validar firma
            .build()
            .parseClaimsJws(token)
            // Parsea y valida el token
            .getBody();
            // Obtiene los claims del token
    }


    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
         // Extrae un claim específico usando una función
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
        // Extrae la fecha de expiración del token
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
        // Verifica si el token ha expirado
    }
}

