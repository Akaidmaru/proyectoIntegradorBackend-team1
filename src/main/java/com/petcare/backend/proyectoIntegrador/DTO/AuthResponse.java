package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.ERole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private ERole role;
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String message;
    private String error;
}
