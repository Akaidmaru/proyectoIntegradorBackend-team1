package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.ServicioImagen;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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

    public ServicioSinCategoriaResponse(Integer idServicio, String nombre, String descripcion, BigDecimal precio, List<ServicioImagen> imagenUrls, Boolean disponibilidad, LocalDateTime fechaRegistro, LocalDateTime fechaActualizacion, boolean esBorrado, LocalDateTime fechaBorrado, Double rating) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrls = imagenUrls;
        this.disponibilidad = disponibilidad;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
        this.esBorrado = esBorrado;
        this.fechaBorrado = fechaBorrado;
        this.rating = rating;
    }
}
