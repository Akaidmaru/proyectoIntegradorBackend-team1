package com.petcare.backend.proyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "caracteristicas")
public class Caracteristicas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCaracteristica;

    private String nombre;

    private String icon;

}
