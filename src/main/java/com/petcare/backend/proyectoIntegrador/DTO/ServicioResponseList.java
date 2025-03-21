package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ServicioResponseList {
    private Integer idServicio;
    private String descripcion;
    private String nombre;
    private Long idCategoria;
    private String categoriaNombre;
}
