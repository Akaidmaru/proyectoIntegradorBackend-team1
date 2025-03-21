package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ServicioResponseSuggestions {
    private Long idCategoria;
    private String categoriaNombre;
    private Integer idServicio;
    private String servicioNombre;
    private String primeraImagen;
}
