package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reserva_fecha")
public class ReservaFecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_fecha")
    private Integer idReservaFecha;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonIgnore
    private Reserva reserva;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
}

