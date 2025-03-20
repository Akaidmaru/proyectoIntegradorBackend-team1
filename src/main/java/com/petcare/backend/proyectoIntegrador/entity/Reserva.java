package com.petcare.backend.proyectoIntegrador.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una reserva de servicio.
 * Relaciona una mascota con un establecimiento para un servicio específico.
 */
@Data
@Entity
@Table(name = "reserva")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer idReserva;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaFecha> fechas;

    @Column(name = "estado")
    private String estado; // TODO: Convertir a enum (PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA)

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "es_borrado")
    private boolean esBorrado;

    @Column(name = "fecha_borrado")
    private LocalDateTime fechaBorrado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_especie")
    @JsonIgnore
    private Especie especie;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    @JsonIgnore
    private Servicio servicio;
}

