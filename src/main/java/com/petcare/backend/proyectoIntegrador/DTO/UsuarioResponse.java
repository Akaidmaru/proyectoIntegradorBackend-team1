package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UsuarioResponse {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private ERole role;

}
