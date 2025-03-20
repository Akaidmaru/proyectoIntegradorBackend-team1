package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "caracteristica_valor")
public class CaracteristicaValor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCaracteristicaValor;

    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    @JsonIgnore
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "id_caracteristica")
    private Caracteristicas caracteristica;



}
