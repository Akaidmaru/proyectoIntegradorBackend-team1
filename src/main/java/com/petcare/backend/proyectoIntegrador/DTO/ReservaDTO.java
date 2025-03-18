package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.ReservaFecha;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class ReservaDTO {
    private List<ReservaFechaDTO> fechas;
    private String estado;
    private Integer idUsuario;
    private Integer idMascota;
    private Integer idEstablecimiento;
    private Integer idServicio;
}

