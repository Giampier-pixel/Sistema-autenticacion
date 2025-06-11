package com.example.demo_jwt.Jwt;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException,IOException {

            
        final String token = getTokenFromRequest(request);
        // Extrae el token JWT del header Authorization
        final String username;


        if (token == null)
        {
            filterChain.doFilter(request, response);
            return;
             // Si no hay token, continúa con la cadena de filtros
        }

        username = jwtService.getUsernameFromToken(token);
        // Extrae el username del token JWT

        if( username != null && SecurityContextHolder.getContext().getAuthentication()==null)
        // Si hay username y no hay autenticación previa
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Carga los detalles del usuario
            if(jwtService.isTokenValid(token, userDetails))
             // Valida si el token es válido
            {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                    // Crea token de autenticación de Spring Security
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Establece detalles de la petición
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // Establece la autenticación en el contexto de seguridad
            }
        }

        filterChain.doFilter(request, response);
        // Continúa con la cadena de filtros

    }

    private String getTokenFromRequest(HttpServletRequest request) 
    {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Obtiene el header Authorization
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
         // Verifica que el header tenga contenido y empiece con "Bearer "
        {
            return authHeader.substring(7);
            // Retorna el token sin el prefijo "Bearer "
        }
        return null;
    }
}
