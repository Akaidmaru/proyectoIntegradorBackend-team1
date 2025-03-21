package com.petcare.backend.proyectoIntegrador.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "especie")
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEspecie;

    @Column(name = "nombre_especie", columnDefinition = "TEXT")
    private String nombreEspecie;

}
