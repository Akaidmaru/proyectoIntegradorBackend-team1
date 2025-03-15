package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoriaRequest {
    private String nombre;
    private String descripcion;
    private String imagenUrl;
}