package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.CaracteristicaValor;
import com.petcare.backend.proyectoIntegrador.entity.ServicioImagen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ServicioSinCategoriaResponse {
    private Integer idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private List<ServicioImagen> imagenUrls;
    private Boolean disponibilidad;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private boolean esBorrado;
    private LocalDateTime fechaBorrado;
    private Double rating;
    private List<CaracteristicaDTO> caracteristicas;
}
