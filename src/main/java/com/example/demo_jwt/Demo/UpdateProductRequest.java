package com.example.demo_jwt.Demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private Integer stock;
    private String descripcion;
}