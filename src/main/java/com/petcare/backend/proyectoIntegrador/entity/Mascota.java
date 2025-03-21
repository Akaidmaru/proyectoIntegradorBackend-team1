package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Entidad que representa una mascota registrada en el sistema.
 * Cada mascota pertenece a un usuario y puede tener múltiples reservas.
 */
@Data
@Entity
@Table(name = "mascota")
public class Mascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "especie")
    private String especie; // Ejemplo: PERRO, GATO, etc.
    
    @Column(name = "raza")
    private String raza;
    
    @Column(name = "edad")
    private Integer edad;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_borrado")
    private boolean esBorrado;
    
    @Column(name = "fecha_borrado")
    private LocalDateTime fechaBorrado;
    
    /**
     * Usuario propietario de la mascota.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;
} 
