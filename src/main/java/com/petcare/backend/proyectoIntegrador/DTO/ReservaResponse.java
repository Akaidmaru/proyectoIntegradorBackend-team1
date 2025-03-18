package com.petcare.backend.proyectoIntegrador.DTO;

import com.petcare.backend.proyectoIntegrador.entity.Reserva;
import com.petcare.backend.proyectoIntegrador.entity.ReservaFecha;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReservaResponse {
    private Integer idReserva;
    private String servicio;
    private List<ReservaFecha> fechas;

    // ✅ Constructor que recibe un objeto Reserva
    public ReservaResponse(Reserva reserva) {
        this.idReserva = reserva.getIdReserva();
        this.servicio = reserva.getServicio().getNombre();
        this.fechas = reserva.getFechas();
    }


    public Integer getId() { return idReserva; }
    public void setId(Integer id) { this.idReserva = id; }

}
