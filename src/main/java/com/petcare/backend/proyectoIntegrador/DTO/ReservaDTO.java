package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ReservaDTO {
    private List<ReservaFechaDTO> fechas;
    private String estado;
    private Integer idUsuario;
    private Integer idEspecie;
    private Integer idServicio;
}

