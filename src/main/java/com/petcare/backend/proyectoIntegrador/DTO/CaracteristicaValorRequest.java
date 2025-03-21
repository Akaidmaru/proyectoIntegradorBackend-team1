package com.petcare.backend.proyectoIntegrador.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CaracteristicaValorRequest {
    private Integer idCaracteristica;
    private Integer idServicio;
    private String valor;
}
